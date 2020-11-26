package com.chriniko.pollfish.kafka.consumer.infra

import akka.Done
import akka.actor.{ActorSystem, Terminated}
import akka.kafka.scaladsl.Consumer.DrainingControl
import akka.kafka.scaladsl.{Committer, Consumer}
import akka.kafka.{CommitterSettings, ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.RunnableGraph
import com.chriniko.pollfish.kafka.consumer.de.MessageDeserializer
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{BytesDeserializer, StringDeserializer}
import org.apache.kafka.common.{Metric, MetricName}
import org.apache.logging.log4j.Logger

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContextExecutor, Future}

class LoggingEventsConsumer(val messageDeserializer: MessageDeserializer) {

  private val log: Logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass)

  private val config = ConfigFactory.load("application.conf")

  private implicit val system: ActorSystem = ActorSystem("logging-events-consumer", config)
  //system.logConfiguration()

  private implicit val dispatcher: ExecutionContextExecutor = system.dispatchers.lookup("akka.kafka.io-blocking-dispatcher")

  private implicit val materializer: ActorMaterializer = ActorMaterializer()

  private implicit val controls: ListBuffer[DrainingControl[Done]] = ListBuffer[DrainingControl[Done]]()

  def getControls: List[DrainingControl[Done]] = {
    controls.toList
  }

  def run(topic: String, noOfPartitions: Int, groupId: String, noOfFlows: Int): Unit = {

    val flows: Seq[RunnableGraph[DrainingControl[Done]]] = (1 to noOfFlows).map { idx =>

      val consumerConfig = config.getConfig("akka.kafka.consumer")
      val consumerSettings =
        ConsumerSettings(consumerConfig, new StringDeserializer, new BytesDeserializer)
          .withGroupId(groupId)
          .withClientId(s"${groupId}__clientId${idx}")
          .withBootstrapServers("localhost:19092")
          .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
          .withProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")

      val committerConfig = config.getConfig("akka.kafka.committer")
      val committerSettings = CommitterSettings(committerConfig)

      /*
        Note: The ConsumerSettings stop-timeout delays stopping the Kafka Consumer and the stream,
        but when using drainAndShutdown that delay is not required and can be set to zero.

        https://doc.akka.io/docs/alpakka-kafka/current/consumer.html#draining-control
       */
      val cSettings = consumerSettings.withStopTimeout(Duration.Zero)

      val subscription = Subscriptions.topics(topic)

      val flow: RunnableGraph[DrainingControl[Done]] =
        Consumer
          .committableSource(cSettings, subscription)
          .mapAsync(noOfPartitions) { msg =>

            // Note: business processing logic.
            messageDeserializer
              .process(msg.record.key(), msg.record.value())
              .map(_ => msg.committableOffset)

          }
          .toMat(Committer.sink(committerSettings))(DrainingControl.apply)

      flow
    }

    val cs: Seq[DrainingControl[Done]] = flows.map { flow => flow.run() }
    controls ++= cs
  }

  def stop(): Future[(Seq[Done] /*controls statuses*/ , Terminated /*system status*/ )] = {
    val fs: Seq[Future[Done]] = controls.toList.map { control => control.drainAndShutdown() }
    val f: Future[Seq[Done]] = Future.sequence(fs)

    for {
      r1 <- f
      _ = materializer.shutdown()
      r2 <- system.terminate()
    } yield {
      (r1, r2)
    }

  }

  def displayMetrics(control: DrainingControl[Done]): Unit = {
    val metrics: Future[Map[MetricName, Metric]] = control.metrics
    metrics.foreach(map => log.info(s"\n\nmetrics: ${map.mkString("\n")}"))
  }


}

package com.chriniko.pollfish.kafka.consumer

import java.util.UUID

import akka.Done
import akka.kafka.scaladsl.Consumer
import com.chriniko.pollfish.kafka.consumer.db.CassandraStore
import com.chriniko.pollfish.kafka.consumer.de.LoggingEventDeserializer
import com.chriniko.pollfish.kafka.consumer.infra.LoggingEventsConsumer
import org.apache.logging.log4j.Logger

import scala.language.postfixOps

object BootstrapKafkaConsumer {

  private val log: Logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {

    val cassandraStore = new CassandraStore()
    val messageDeserializer = new LoggingEventDeserializer(cassandraStore)

    val kafkaConsumer = new LoggingEventsConsumer(messageDeserializer)

    kafkaConsumer.run("log-events", 16, s"group_${UUID.randomUUID()}", 1)

    val controls: Seq[Consumer.DrainingControl[Done]] = kafkaConsumer.getControls
    log.info(s"controls created: ${controls.size}")


    // Note: if we want to terminate uncomment following section:
    /*

    Thread.sleep(15_000)

    val f: Future[(Seq[Done], Terminated)] = kafkaConsumer.stop()
    Await.result(f, 25000 millis)

    log.info("finished...")

     */

  }

}

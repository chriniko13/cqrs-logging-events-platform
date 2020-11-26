package com.chriniko.pollfish.clients.simulator.infra

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{Executors, ThreadFactory}

import com.chriniko.pollfish.clients.simulator.infra.simulation.{AsyncClientSimulation, ClientSimulation, SyncClientSimulation}
import com.chriniko.pollfish.thrift.client.domain.LoggingEventClientError
import com.chriniko.pollfish.thrift.server.protocol.LoggingEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.Logger

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService, Future}
import scala.util.{Failure, Success}

class ClientSimulationsCoordinator(private val failHard: Boolean) {

  protected val log: Logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass)

  private implicit val mapper: ObjectMapper = new ObjectMapper();

  private implicit val executionContext: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(
    Executors.newFixedThreadPool(120, new ThreadFactory {

      val idx = new AtomicInteger()

      override def newThread(runnable: Runnable): Thread = {
        val t = new Thread(runnable)
        t.setName(s"client-sim-coord-${idx.getAndIncrement()}")
        t
      }
    }))

  // ---

  def runWithSyncClient(clients: Int): Future[Seq[FlowResult]] = {
    run(clients, createFlow(new SyncClientSimulation))
  }

  def runWithAsyncClient(clients: Int): Future[Seq[FlowResult]] = {
    run(clients, createFlow(new AsyncClientSimulation))
  }

  // ---


  private def run(clients: Int, flowResult: Future[FlowResult]): Future[Seq[FlowResult]] = {
    val fs: Seq[Future[FlowResult]] = (1 to clients).map { _ => registerResultHandler(flowResult) }
    val f: Future[Seq[FlowResult]] = Future.sequence(fs)
    f
  }

  private def createFlow(client: ClientSimulation): Future[FlowResult] = {

    val flow: Future[FlowResult] = for {
      res1 <- client.monitorServiceTrafficMaker()
      res2 <- client.transactionTrafficMaker()
      res3 <- client.userActionTrafficMaker()
    } yield {
      FlowResult(res1, res2, res3)
    }

    flow
  }

  private def registerResultHandler(flow: Future[FlowResult])(implicit mapper: ObjectMapper): Future[FlowResult] = {

    flow.onComplete {
      case Failure(exception) =>
        log.error(s"clientFlow failure, message: ${exception.getMessage}", exception)

      case Success(value) =>
        if (value.anyError) {
          log.error(s"clientFlow failure, message: ${value.info}")
          if (failHard) System.exit(-1)

        } else {
          log.info(s"clientFlow success, message: ${value.info}\n")
        }
    }

    flow
  }


  // --- infra ---

  type TrafficMakerResult = Either[LoggingEventClientError, LoggingEvent]

  case class FlowResult(monitorResult: TrafficMakerResult, transactionResult: TrafficMakerResult, userActionResult: TrafficMakerResult) {

    def info: String = {

      val sb = new StringBuilder()

      val f: (Any, StringBuilder) => Unit = (x, _sb) => _sb.append(s"\n   ${x.toString}")

      monitorResult.fold(f(_, sb), f(_, sb))
      transactionResult.fold(f(_, sb), f(_, sb))
      userActionResult.fold(f(_, sb), f(_, sb))

      sb.toString()
    }

    def anyError: Boolean = {
      monitorResult.isLeft || transactionResult.isLeft || userActionResult.isLeft
    }
  }

}

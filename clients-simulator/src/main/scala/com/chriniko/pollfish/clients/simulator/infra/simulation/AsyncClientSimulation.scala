package com.chriniko.pollfish.clients.simulator.infra.simulation

import java.util.concurrent.ThreadLocalRandom

import com.chriniko.pollfish.thrift.client.LoggingEventServiceAsyncClient
import com.chriniko.pollfish.thrift.client.domain.{HealthStatusResponse, LogEvent, LoggingEventClientError}
import com.chriniko.pollfish.thrift.server.protocol.LoggingEvent
import com.fasterxml.jackson.databind.ObjectMapper

import scala.concurrent.{ExecutionContext, Future}

class AsyncClientSimulation(implicit val mapper: ObjectMapper, val executionContext: ExecutionContext) extends ClientSimulation {


  private implicit val clientSupplier: () => LoggingEventServiceAsyncClient = () => new LoggingEventServiceAsyncClient("localhost", 9090, mapper)

  private implicit val pullHealthCheck: () => Future[Either[LoggingEventClientError, HealthStatusResponse]] = () => clientSupplier().healthStatus()


  // ---

  override def checkStatus(): Future[Unit] = {
    pullHealthCheck().map(resp => printCheckStatusResponse(() => resp))
  }

  override def userActionTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]] = {
    val evt = createUserActionEvent()
    sendToServer(evt)
  }

  override def monitorServiceTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]] = {
    val evt = createMonitorServiceEvent()
    sendToServer(evt)
  }

  override def transactionTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]] = {
    val evt = createTransactionEvent()
    sendToServer(evt)
  }


  // ---

  private def sendToServer(logEvt: LogEvent)(implicit client: () => LoggingEventServiceAsyncClient): Future[Either[LoggingEventClientError, LoggingEvent]] = {
    val addOptionalProperties = ThreadLocalRandom.current().nextInt(0, 2)
    if (addOptionalProperties == 1) {

      val toSave = enrichLogEvent(logEvt)
      client().save(toSave)

    } else {
      client().save(logEvt)
    }
  }

  override def classType: Class[_] = this.getClass
}

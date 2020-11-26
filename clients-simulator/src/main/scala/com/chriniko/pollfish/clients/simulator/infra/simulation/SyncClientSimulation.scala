package com.chriniko.pollfish.clients.simulator.infra.simulation

import java.util.concurrent.ThreadLocalRandom

import com.chriniko.pollfish.thrift.client.LoggingEventServiceSyncClient
import com.chriniko.pollfish.thrift.client.domain.{HealthStatusResponse, LogEvent, LoggingEventClientError}
import com.chriniko.pollfish.thrift.server.protocol.LoggingEvent
import com.fasterxml.jackson.databind.ObjectMapper

import scala.concurrent.{ExecutionContext, Future}

class SyncClientSimulation(implicit val mapper: ObjectMapper, val executionContext: ExecutionContext) extends ClientSimulation {

  private implicit val clientSupplier: () => LoggingEventServiceSyncClient = () => new LoggingEventServiceSyncClient("localhost", 9090, mapper)

  private implicit val pullHealthCheck: () => Either[LoggingEventClientError, HealthStatusResponse] = () => clientSupplier().healthStatus().value


  // ---

  override def checkStatus(): Future[Unit] = Future(printCheckStatusResponse)

  override def userActionTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]] = {
    Future {
      val evt = createUserActionEvent()
      sendToServer(evt)
    }
  }

  override def monitorServiceTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]] = {
    Future {
      val evt = createMonitorServiceEvent()
      sendToServer(evt)
    }
  }

  override def transactionTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]] = {
    Future {
      val evt = createTransactionEvent()
      sendToServer(evt)
    }
  }


  // ---

  private def sendToServer(logEvt: LogEvent)(implicit client: () => LoggingEventServiceSyncClient): Either[LoggingEventClientError, LoggingEvent] = {
    val addOptionalProperties = ThreadLocalRandom.current().nextInt(0, 2)

    if (addOptionalProperties == 1) {
      val toSave = enrichLogEvent(logEvt)
      client().save(toSave).value

    } else {
      client().save(logEvt).value
    }
  }

  override def classType: Class[_] = this.getClass
}

package com.chriniko.pollfish.thrift.client

import com.chriniko.pollfish.thrift.client.domain.LogEvent
import com.chriniko.pollfish.thrift.server.protocol.{LoggingEvent, LoggingEventService, LoggingEventServiceHealthStatus}
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.annotation.NotThreadSafe
import org.apache.thrift.async.{AsyncMethodCallback, TAsyncClientManager}
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.transport.TNonblockingSocket

import scala.concurrent.{Future, Promise}

/**
 * Important Note: this async client should be used only if the thrift-server is using: TThreadedSelectorServer.java server flavour.
 *
 * @param host
 * @param port
 * @param mapper
 */
@NotThreadSafe
class LoggingEventServiceAsyncClient(val host: String, val port: Int, val mapper: ObjectMapper) extends LoggingEventServiceClient[Future] {


  val transport = new TNonblockingSocket(host, port)
  val clientManager = new TAsyncClientManager
  val protocolFactory = new TBinaryProtocol.Factory

  val client = new LoggingEventService.AsyncClient(protocolFactory, clientManager, transport)


  override def save(log: LogEvent): Future[LogResponse] = {

    val evt = constructLoggingEvent(log, mapper)

    val p = Promise[LogResponse]()
    val f = p.future

    client.save(evt, new AsyncMethodCallback[LoggingEvent] {
      override def onComplete(response: LoggingEvent): Unit = {
        p.success(Right(response))
      }

      override def onError(exception: Exception): Unit = {
        p.success(Left(errorMapping(exception)))
      }
    })

    f
  }

  override def save(data: Seq[LogEvent]): List[Future[LogResponse]] =
    data.map(x => save(x)).toList

  override def healthStatus(): Future[HealthResponse] = {

    val p = Promise[HealthResponse]()
    val f = p.future

    client.healthStatus(new AsyncMethodCallback[LoggingEventServiceHealthStatus] {
      override def onComplete(response: LoggingEventServiceHealthStatus): Unit = {
        p.success(
          serializer(response.healthy, response.msg, mapper).asEither
        )
      }

      override def onError(exception: Exception): Unit = {
        p.success(Left(errorMapping(exception)))
      }
    })

    f
  }
}

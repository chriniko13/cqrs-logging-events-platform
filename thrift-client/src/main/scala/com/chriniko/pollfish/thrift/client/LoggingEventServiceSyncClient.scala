package com.chriniko.pollfish.thrift.client

import com.chriniko.pollfish.thrift.client.LoggingEventServiceClient.AsIs
import com.chriniko.pollfish.thrift.client.domain.LogEvent
import com.chriniko.pollfish.thrift.server.protocol._
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.annotation.NotThreadSafe
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.transport.TSocket

import scala.util.Try

@NotThreadSafe
class LoggingEventServiceSyncClient(val host: String, val port: Int, val mapper: ObjectMapper) extends LoggingEventServiceClient[AsIs] {

  // --- attributes ---
  private val transport = new TSocket(host, port)
  transport.open()

  private val protocol = new TBinaryProtocol(transport)
  private val client = new LoggingEventService.Client(protocol)


  // --- methods ---

  import LoggingEventServiceClient._

  override def save(log: LogEvent): AsIs[LogResponse] = {
    Try {
      val evt: LoggingEvent = constructLoggingEvent(log, mapper)
      client.save(evt)
    }
      .asEither
      .asIs
  }

  override def save(data: Seq[LogEvent]): List[AsIs[LogResponse]] =
    data.map(rec => save(rec)).toList

  override def healthStatus(): AsIs[HealthResponse] = {
    Try(client.healthStatus()).flatMap(x => serializer(x.healthy, x.msg, mapper)).asEither.asIs
  }


}


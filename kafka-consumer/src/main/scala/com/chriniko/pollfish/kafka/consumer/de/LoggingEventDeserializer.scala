package com.chriniko.pollfish.kafka.consumer.de

import akka.Done
import com.chriniko.pollfish.kafka.consumer.db.CassandraStore
import com.chriniko.pollfish.thrift.server.protocol.LoggingEvent
import org.apache.kafka.common.utils.Bytes
import org.apache.logging.log4j.Logger
import org.apache.thrift.TDeserializer

import scala.concurrent.{ExecutionContext, Future}

class LoggingEventDeserializer(val cassandraStore: CassandraStore) extends MessageDeserializer {

  private val log: Logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass)

  private val tDeserializerThreadLocal: ThreadLocal[TDeserializer] = ThreadLocal.withInitial[TDeserializer](() => new TDeserializer())

  def process(key: String, value: Bytes)(implicit ex: ExecutionContext): Future[Done] = {
    Future(extractMessage(value))
      .map { payload =>

        log.debug(s">>>> event consumed: ${payload}")
        cassandraStore.store(payload)

        Done.done()
      }
  }

  def extractMessage(value: Bytes): LoggingEvent = {
    val deserializer = tDeserializerThreadLocal.get()
    val loggingEvent: LoggingEvent = new LoggingEvent()
    deserializer.deserialize(loggingEvent, value.get())
    loggingEvent
  }


}

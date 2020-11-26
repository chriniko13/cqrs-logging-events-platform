package com.chriniko.pollfish.thrift.client

import java.io.IOException
import java.nio.ByteBuffer
import java.time.Instant
import java.util.UUID

import com.chriniko.pollfish.thrift.client.domain.LoggingEventClientError.{GeneralCommError, GeneralError, JsonParsingError, NotValidInputError}
import com.chriniko.pollfish.thrift.client.domain.{ErrorInfo, HealthStatusResponse, LogEvent, LoggingEventClientError}
import com.chriniko.pollfish.thrift.server.protocol.{LoggingEvent, LoggingEventPayload, NotValidInputException}
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.thrift.TException

import scala.annotation.tailrec
import scala.language.higherKinds
import scala.util.{Failure, Success, Try}

trait LoggingEventServiceClient[F[_]] {

  // type aliases
  type LogResponse = Either[LoggingEventClientError, LoggingEvent]

  type HealthResponse = Either[LoggingEventClientError, HealthStatusResponse]

  // contracts
  def save(log: LogEvent): F[LogResponse]

  def save(data: Seq[LogEvent]): List[F[LogResponse]]

  def healthStatus(): F[HealthResponse]

  // --- infra/helper ---

  protected def constructLoggingEvent(log: LogEvent, mapper: ObjectMapper): LoggingEvent = {

    val payload: LoggingEventPayload = log.payload
    val evt = new LoggingEvent(UUID.randomUUID().toString, log.origin, Instant.now().toEpochMilli, payload)

    evt.priority = log.priority

    import scala.jdk.CollectionConverters._
    evt.metadata = log.metadata.asJava

    evt
  }

  protected def serializer(healthy: Boolean, byteBuffer: ByteBuffer, mapper: ObjectMapper): Try[HealthStatusResponse] = {
    Try {
      HealthStatusResponse(healthy, mapper.readValue[String](byteBuffer.array(), classOf[String]))
    }
  }

  protected val errorMapping: Throwable => LoggingEventClientError = {
    case e: NotValidInputException => NotValidInputError(e)
    case e: TException => GeneralCommError(e)
    case e: IOException => JsonParsingError(e)
    case e: Throwable => GeneralError(e)
  }


  protected implicit class TryOps[L >: LoggingEventClientError, R](r: Try[R]) {

    def asEither: Either[L, R] = r match {
      case Success(something) => Right(something)
      case Failure(e) => Left(errorMapping(e))
    }
  }

}


// --- companion object ---

object LoggingEventServiceClient {

  case class AsIs[T](t: T) {
    def value: T = t
  }

  implicit class AsIsOps[T](val t: T) {
    def asIs: AsIs[T] = AsIs(t)
  }

  def unwrapAndCollectErrors(e: LoggingEventClientError): List[ErrorInfo] = {

    @tailrec
    def helper(e: Throwable, acc: List[ErrorInfo]): List[ErrorInfo] = {
      if (e != null) {
        val errorInfo = ErrorInfo(e.getMessage, e.getStackTrace.mkString("\n"))
        helper(e.getCause, acc :+ errorInfo)
      } else {
        acc
      }
    }

    helper(e.capturedError, List())
  }

}

package com.chriniko.pollfish.thrift.client.domain

import java.io.IOException

import com.chriniko.pollfish.thrift.server.protocol.NotValidInputException
import org.apache.thrift.TException

// Algebraic Data Type
sealed abstract class LoggingEventClientError extends Product with Serializable {
  def capturedError: Throwable
}

object LoggingEventClientError {

  final case class NotValidInputError(capturedError: NotValidInputException) extends LoggingEventClientError

  final case class GeneralCommError(capturedError: TException) extends LoggingEventClientError

  final case class GeneralError(capturedError: Throwable) extends LoggingEventClientError

  final case class JsonParsingError(capturedError: IOException) extends LoggingEventClientError

}
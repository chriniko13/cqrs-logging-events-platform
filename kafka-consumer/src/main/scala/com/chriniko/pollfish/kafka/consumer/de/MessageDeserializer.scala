package com.chriniko.pollfish.kafka.consumer.de

import akka.Done
import org.apache.kafka.common.utils.Bytes

import scala.concurrent.{ExecutionContext, Future}

trait MessageDeserializer {

  def process(key: String, value: Bytes)(implicit ex: ExecutionContext): Future[Done]

}

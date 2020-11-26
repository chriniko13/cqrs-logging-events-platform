package com.chriniko.pollfish.kafka.consumer.db

import java.time.Instant
import java.util.UUID

case class LogEventRecord(version: Int,
                          id: UUID,
                          origin: String,
                          time: Instant,
                          metadata: Map[String, String],
                          `type`: String,
                          priority: String,
                          payload: String)

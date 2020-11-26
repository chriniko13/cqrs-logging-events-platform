package com.chriniko.pollfish.thrift.client.domain

import com.chriniko.pollfish.thrift.server.protocol.{LoggingEventPayload, LoggingEventPriority}

case class LogEvent(origin: String,
                    payload: LoggingEventPayload,
                    metadata: Map[String, String] = Map.empty,
                    priority: LoggingEventPriority = LoggingEventPriority.NORMAL)

package com.chriniko.pollfish.kafka.consumer.db

import java.time.temporal.ChronoUnit
import java.time.{Duration, Instant}
import java.util
import java.util.{Collections, UUID}

import com.chriniko.pollfish.thrift.server.protocol.LoggingEvent
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.{BatchableStatement, DefaultBatchType, SimpleStatement, Statement}
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.datastax.oss.driver.api.querybuilder.QueryBuilder._
import com.datastax.oss.driver.internal.core.cql.DefaultBatchStatement
import org.apache.logging.log4j.Logger

import scala.collection.mutable.ListBuffer
import scala.io.Source

class CassandraStore {

  private val log: Logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass)
  private val session: CqlSession = CqlSession.builder().build()

  loadScript()

  private def loadScript(): Unit = {

    val acc = ListBuffer[String]()

    def f(): Unit = {
      val q = acc.mkString
      log.debug(s"will execute query: $q")
      session.execute(q)
    }

    Source.fromResource("script/cassandra_db_setup.cql").getLines().foreach { line =>
      if (line == "--##--") {

        if (acc.nonEmpty) {
          f()
          acc.clear()
        }

      } else {
        if (line.nonEmpty && line != "--##--") acc += line
      }
    }

    if (acc.nonEmpty) {
      f()
    }

  }


  def store(evt: LoggingEvent): Boolean = {

    val whichType = if (evt.m.isSetMonitorServiceEvent) "MONITOR_SERVICE_EVENT"
    else if (evt.m.isSetTransactionEvent) "TRANSACTION_EVENT"
    else "USER_INFO_EVENT"

    def _stat(tableName: String) = createStatement(tableName, evt, whichType)

    val statements: Seq[SimpleStatement] = List(
      "logging_evts_by_type_ord_time_desc",
      "logging_evts_by_priority_ord_time_desc",
      "logging_evts_by_priority_by_type_ord_time_desc",
      "logging_evts_by_or_by_prio_by_typ_ord_t_desc",
      "logging_evts_by_or_ord_prio_typ_t_desc"
    ).map(_stat)

    import scala.jdk.CollectionConverters._
    val l = new util.ArrayList[BatchableStatement[_]](statements.asJava)
    val batchStatement = constructBatchStatement(l)

    session.execute(batchStatement).wasApplied()
  }

  // TODO: IMPORTANT IMPROVE - USE preparedStatement
  private def createStatement(tableName: String, evt: LoggingEvent, whichType: String): SimpleStatement = {
    QueryBuilder.insertInto(tableName)
      .value("version", literal(evt.v))
      .value("id", literal(UUID.fromString(evt.id)))
      .value("origin", literal(evt.origin))
      .value("time", literal(Instant.ofEpochMilli(evt.time)))
      .value("metadata", literal(evt.metadata))
      .value("type", literal(whichType))
      .value("priority", literal(evt.priority.name()))
      .value("payload", literal(evt.toString))
      .build()
  }

  private def constructBatchStatement(l: util.ArrayList[BatchableStatement[_]]) = {
    new DefaultBatchStatement(
      DefaultBatchType.LOGGED,
      l,
      null,
      null,
      null,
      null,
      null,
      null,
      Collections.emptyMap(),
      null,
      false,
      Statement.NO_DEFAULT_TIMESTAMP,
      null,
      Integer.MIN_VALUE,
      null,
      null,
      Duration.of(2000, ChronoUnit.MILLIS),
      null,
      Statement.NO_NOW_IN_SECONDS)
  }


}

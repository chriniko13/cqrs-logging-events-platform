package com.chriniko.pollfish.clients.simulator.infra.simulation

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

import com.chriniko.pollfish.thrift.client.LoggingEventServiceClient
import com.chriniko.pollfish.thrift.client.domain.{ErrorInfo, HealthStatusResponse, LogEvent, LoggingEventClientError}
import com.chriniko.pollfish.thrift.server.protocol._
import org.apache.logging.log4j.Logger

import scala.concurrent.Future

trait ClientSimulation {

  protected val log: Logger = org.apache.logging.log4j.LogManager.getLogger(classType)

  def classType: Class[_]

  def checkStatus(): Future[Unit]

  def userActionTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]]

  def monitorServiceTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]]

  def transactionTrafficMaker(): Future[Either[LoggingEventClientError, LoggingEvent]]


  protected def createUserActionEvent(): LogEvent = {

    val userServiceNode = ThreadLocalRandom.current().nextInt(0, 3)

    val userId = UUID.randomUUID().toString

    val userInfoEvent = new UserInfoEvent()
    userInfoEvent.setUserId(userId)

    val t: UserInfoEventType = ThreadLocalRandom.current().nextInt(0, 3) match {
      case 0 =>
        val t = new LoggedIn("http://system-login.com")
        val r = new UserInfoEventType()
        r.setLoggedIn(t)
        r

      case 1 =>
        val t = new LoggedOut(ThreadLocalRandom.current().nextLong(60, 60 * 20))
        val r = new UserInfoEventType()
        r.setLoggedOut(t)
        r

      case 2 =>
        val t = new ClickedOnAdvertisement("http://click-url.com", "http://tracker-url.com")
        val r = new UserInfoEventType()
        r.setClickedOnAdvertisement(t)
        r
    }

    userInfoEvent.setType(t)

    val payload = new LoggingEventPayload()
    payload.setUserInfoEvent(userInfoEvent)

    val logEvt = LogEvent(s"user_service_$userServiceNode", payload)
    logEvt
  }


  protected def createMonitorServiceEvent(): LogEvent = {

    val monitorServiceNode = ThreadLocalRandom.current().nextInt(0, 3)

    val serviceId = UUID.randomUUID().toString

    val monitorServiceEvent = new MonitorServiceEvent()
    monitorServiceEvent.setServiceId(serviceId)
    monitorServiceEvent.setServiceUrl("http://service.com")


    val monitorServiceReportType: InfoEventType = ThreadLocalRandom.current().nextInt(0, 3) match {
      case 0 => InfoEventType.LOW_MEMORY
      case 1 => InfoEventType.CPU_IDLE
      case 2 => InfoEventType.CPU_HOT
    }

    monitorServiceEvent.setType(monitorServiceReportType)

    val payload = new LoggingEventPayload()
    payload.setMonitorServiceEvent(monitorServiceEvent)

    val logEvt = LogEvent(s"monitor_service_$monitorServiceNode", payload)
    logEvt
  }


  protected def createTransactionEvent(): LogEvent = {

    val walletServiceNode = ThreadLocalRandom.current().nextInt(0, 3)

    val tx = new TransactionEvent(UUID.randomUUID().toString, "1", "2", 6400.0D)

    val payload = new LoggingEventPayload()
    payload.setTransactionEvent(tx)

    val logEvt = LogEvent(s"wallet_service_$walletServiceNode", payload)

    logEvt
  }


  protected def printCheckStatusResponse(implicit pull: () => Either[LoggingEventClientError, HealthStatusResponse]): Unit = {
    pull() match {
      case Left(error) =>
        val s: Seq[ErrorInfo] = LoggingEventServiceClient.unwrapAndCollectErrors(error)
        s.foreach(s => log.info(s"error occurred in health status, message: ${s.msg} -- trace: ${s.trace}"))


      case Right(success) =>
        log.info(s"is healthy: ${success.healthy} \n ${success.info}")
    }
  }

  protected def enrichLogEvent(logEvt: _root_.com.chriniko.pollfish.thrift.client.domain.LogEvent) =  {
    val priorityToSelect = ThreadLocalRandom.current().nextInt(0, 3)
    val priority = priorityToSelect match {
      case 0 => LoggingEventPriority.NORMAL
      case 1 => LoggingEventPriority.MEDIUM
      case 2 => LoggingEventPriority.CRITICAL
    }

    val toSave = logEvt.copy(
      metadata = Map[String, String]("somekey1" -> "somevalue1", "somekey2" -> "somevalue2", "somekey3" -> "somevalue3"),
      priority = priority
    )
    toSave
  }
}

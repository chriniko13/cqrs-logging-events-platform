package com.chriniko.pollfish.clients.simulator

import java.util.concurrent.locks.LockSupport

import com.chriniko.pollfish.clients.simulator.infra.ClientSimulationsCoordinator
import org.apache.logging.log4j.Logger

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

object BootstrapClientsSimulator {

  private val log: Logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {

    val coordinator = new ClientSimulationsCoordinator(true)
    val always = true


    // Note: keep in mind which client to use based on the thift-server.
    val coordinatorTrafficCreator = () => coordinator.runWithAsyncClient(120)
    //val coordinatorTrafficCreator = () => coordinator.runWithSyncClient(1)

    if (!always) {

      val f: Future[Seq[coordinator.FlowResult]] = coordinatorTrafficCreator()
      Await.result(f, 25000 millis)

    } else {
      while (true) {
        val f: Future[Seq[coordinator.FlowResult]] = coordinatorTrafficCreator()
        Await.result(f, 25000 millis)

        // Note: pacing.
        LockSupport.parkNanos((1 seconds).toNanos)
        log.info("\n\n\n\n")
      }
    }


  }


}

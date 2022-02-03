package com.tsms.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern._
import com.tsms.models.spoileralert.{GetSpoilerAlertReqModel, SpoilerAlertResponseModel}

import java.sql.Connection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * This actor takes care of all the Spoiler Alert Related request
 *
 * @param dbConnection database connection to execute queries
 */
class SpoilerAlertRequestProcessorActor(dbConnection: Connection) extends Actor with ActorLogging {
  override def receive: Receive = {
    case spoilerAlertReqModel: GetSpoilerAlertReqModel =>
      log.info(s"Processing $spoilerAlertReqModel")

      Try {
        dbConnection.createStatement().executeQuery(spoilerAlertReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          val alert = spoilerAlertReqModel.dbModelTransformer.get.buildResult(resultSet)
          if (alert.length == 2) {
            val firstUser: SpoilerAlertResponseModel = alert.head.asInstanceOf[SpoilerAlertResponseModel]
            val secondUser: SpoilerAlertResponseModel = alert.last.asInstanceOf[SpoilerAlertResponseModel]

            if (firstUser.watched_till == secondUser.watched_till) {
              Future(s"No one is ahead") pipeTo sender()
            } else if (firstUser.watched_till > secondUser.watched_till) {
              Future(s"User ${firstUser.user_id} is ahead") pipeTo sender()
            } else {
              Future(s"User ${secondUser.user_id} is ahead") pipeTo sender()
            }
          } else if (alert.length == 1) {
            Future(s"User ${alert.head.asInstanceOf[SpoilerAlertResponseModel].user_id} is ahead") pipeTo sender()
          } else {
            Future("Both User Didn't Started Watching") pipeTo sender()
          }
      }
  }
}


object SpoilerAlertRequestProcessorActor {
  def props(connection: Connection): Props = Props(new SpoilerAlertRequestProcessorActor(connection))
}
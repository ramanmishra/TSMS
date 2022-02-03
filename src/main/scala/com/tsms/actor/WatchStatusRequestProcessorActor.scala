package com.tsms.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern._
import com.tsms.models.episode.{GetWatchStatusReqModel, UpdateWatchStatusReqModel}

import java.sql.Connection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * This actor takes care of all the Watching status Related request
 *
 * @param dbConnection database connection to execute queries
 */
class WatchStatusRequestProcessorActor(dbConnection: Connection) extends Actor with ActorLogging {
  override def receive: Receive = {
    case getWatchStatusReqModel: GetWatchStatusReqModel =>
      log.info(s"Processing $getWatchStatusReqModel")

      Try {
        dbConnection.createStatement().executeQuery(getWatchStatusReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          val users = getWatchStatusReqModel.dbModelTransformer.get.buildResult(resultSet)
          Future(users) pipeTo sender()
      }

    case updateWatchStatusReqModel: UpdateWatchStatusReqModel =>
      log.info(s"Processing $updateWatchStatusReqModel")

      Try {
        dbConnection.createStatement().executeQuery(updateWatchStatusReqModel.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender


        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(updateWatchStatusReqModel.updateQuery.get)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) => Future(true) pipeTo sender
          }
        } else {
          Try {
            dbConnection.createStatement().executeUpdate(updateWatchStatusReqModel.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) => Future(true) pipeTo sender
          }
        }
      }
  }

}

object WatchStatusRequestProcessorActor {
  def props(connection: Connection): Props = Props(new WatchStatusRequestProcessorActor(connection))
}
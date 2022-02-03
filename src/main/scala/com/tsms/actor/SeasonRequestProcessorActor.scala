package com.tsms.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern._
import com.tsms.models.season.{AddSeasonReqModel, DeleteSeasonReqModel, GetSeasonsReqModel}

import java.sql.Connection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * This actor takes care of all the Season Related request
 *
 * @param dbConnection database connection to execute queries
 */
class SeasonRequestProcessorActor(dbConnection: Connection) extends Actor with ActorLogging {
  override def receive: Receive = {
    case addSeasonReqModel: AddSeasonReqModel =>
      log.info(s"Processing $addSeasonReqModel")

      Try {
        dbConnection.createStatement().executeUpdate(addSeasonReqModel.query)
      } match {
        case Failure(_) => Future(false) pipeTo sender()

        case Success(_) => Future(true) pipeTo sender
      }

    case deleteSeasonReqModel: DeleteSeasonReqModel =>
      log.info(s"Processing $deleteSeasonReqModel")

      Try {
        dbConnection.createStatement().executeQuery(deleteSeasonReqModel.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender

        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(deleteSeasonReqModel.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) => Future(true) pipeTo sender
          }
        } else {
          Future(false) pipeTo sender
        }
      }

    case getSeasonsReqModel: GetSeasonsReqModel =>
      log.info(s"Processing $getSeasonsReqModel")

      Try {
        dbConnection.createStatement().executeQuery(getSeasonsReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          val users = getSeasonsReqModel.dbModelTransformer.get.buildResult(resultSet)
          Future(users) pipeTo sender()
      }
  }
}

object SeasonRequestProcessorActor {
  def props(connection: Connection): Props = Props(new SeasonRequestProcessorActor(connection))
}

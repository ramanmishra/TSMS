package com.tsms.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern._
import com.tsms.models.episode.{AddEpisodeReqModel, DeleteEpisodeReqModel, GetEpisodesReqModel}

import java.sql.Connection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * This actor takes care of all the Episode Related request
 *
 * @param dbConnection database connection to execute queries
 */
class EpisodeRequestProcessorActor(dbConnection: Connection) extends Actor with ActorLogging {
  override def receive: Receive = {
    case addEpisodeReqModel: AddEpisodeReqModel =>
      log.info(s"Processing $addEpisodeReqModel")

      Try {
        dbConnection.createStatement().executeUpdate(addEpisodeReqModel.query)
      } match {
        case Failure(_) => Future(false) pipeTo sender()

        case Success(_) => Future(true) pipeTo sender
      }

    case deleteEpisodeReqModel: DeleteEpisodeReqModel =>
      log.info(s"Processing $deleteEpisodeReqModel")

      Try {
        dbConnection.createStatement().executeQuery(deleteEpisodeReqModel.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender

        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(deleteEpisodeReqModel.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) => Future(true) pipeTo sender
          }
        } else {
          Future(false) pipeTo sender
        }
      }

    case getEpisodesReqModel: GetEpisodesReqModel =>
      log.info(s"Processing $getEpisodesReqModel")

      Try {
        dbConnection.createStatement().executeQuery(getEpisodesReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          val users = getEpisodesReqModel.dbModelTransformer.get.buildResult(resultSet)
          Future(users) pipeTo sender()
      }
  }
}

object EpisodeRequestProcessorActor {
  def props(connection: Connection): Props = Props(new EpisodeRequestProcessorActor(connection))
}

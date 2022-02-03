package com.tsms.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern._
import com.tsms.models.rating._

import java.sql.{Connection, ResultSet}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * This actor takes care of all the Rating Related request
 *
 * @param dbConnection database connection to execute queries
 */
class RatingRequestProcessorActor(dbConnection: Connection) extends Actor with ActorLogging {
  def receive: Receive = {
    case getSeriesRatingReqModel: GetSeriesRatingReqModel =>
      Try {
        dbConnection.createStatement().executeQuery(getSeriesRatingReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          if (resultSet.next) {
            val rating = resultSet.getFloat("rating").toString
            Future(rating) pipeTo sender()
          } else {
            Future(s"Please Rating Series ${getSeriesRatingReqModel.seriesId}") pipeTo sender()
          }
      }

    case updateSeriesRatingReqModel: UpdateSeriesRatingReqModel =>
      log.info(s"Processing $updateSeriesRatingReqModel")

      Try {
        dbConnection.createStatement().executeQuery(updateSeriesRatingReqModel.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender

        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(updateSeriesRatingReqModel.updateQuery.get)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) =>
              dbConnection.createStatement().executeUpdate(updateSeriesRatingReqModel.otherTableUpdateQuery.get)
              Future(true) pipeTo sender
          }
        } else {
          Try {
            dbConnection.createStatement().executeUpdate(updateSeriesRatingReqModel.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) =>
              dbConnection.createStatement().executeUpdate(updateSeriesRatingReqModel.otherTableUpdateQuery.get)
              Future(true) pipeTo sender
          }
        }
      }

    case getSeasonRatingReqModel: GetSeasonRatingReqModel =>
      Try {
        dbConnection.createStatement().executeQuery(getSeasonRatingReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          if (resultSet.next) {
            val rating = resultSet.getFloat("rating").toString
            Future(rating) pipeTo sender()
          } else {
            Future(s"Please Rating Season ${getSeasonRatingReqModel.seasonId}") pipeTo sender()
          }
      }

    case updateSeasonRatingReqModel: UpdateSeasonRatingReqModel =>
      log.info(s"Processing $updateSeasonRatingReqModel")

      Try {
        dbConnection.createStatement().executeQuery(updateSeasonRatingReqModel.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender

        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(updateSeasonRatingReqModel.updateQuery.get)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) =>
              dbConnection.createStatement().executeUpdate(updateSeasonRatingReqModel.otherTableUpdateQuery.get)
              Future(true) pipeTo sender
          }
        } else {
          Try {
            dbConnection.createStatement().executeUpdate(updateSeasonRatingReqModel.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) =>
              dbConnection.createStatement().executeUpdate(updateSeasonRatingReqModel.otherTableUpdateQuery.get)
              Future(true) pipeTo sender
          }
        }
      }

    case getEpisodeRatingReqModel: GetEpisodeRatingReqModel =>
      Try {
        dbConnection.createStatement().executeQuery(getEpisodeRatingReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          if (resultSet.next) {
            val rating = resultSet.getFloat("rating").toString
            Future(rating) pipeTo sender()
          } else {
            Future(s"Please Rating Episode ${getEpisodeRatingReqModel.episodeId}") pipeTo sender()
          }
      }

    case updateEpisodeRatingReqModel: UpdateEpisodeRatingReqModel =>
      log.info(s"Processing $updateEpisodeRatingReqModel")

      Try {
        dbConnection.createStatement().executeQuery(updateEpisodeRatingReqModel.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender

        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(updateEpisodeRatingReqModel.updateQuery.get)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) =>
              dbConnection.createStatement().executeUpdate(updateEpisodeRatingReqModel.otherTableUpdateQuery.get)
              Future(true) pipeTo sender
          }
        } else {
          Try {
            dbConnection.createStatement().executeUpdate(updateEpisodeRatingReqModel.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) =>
              dbConnection.createStatement().executeUpdate(updateEpisodeRatingReqModel.otherTableUpdateQuery.get)
              Future(true) pipeTo sender
          }
        }
      }

    case getRecommendationReqModel: GetRecommendationReqModel =>
      Try {
        dbConnection.createStatement().executeQuery(getRecommendationReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          if (resultSet.next()) {
            val interest = resultSet.getString("interest")
            Try {

              val query = getRecommendationReqModel.selectQuery.get.replace("like", s"ilike '%${interest.split(",").mkString("%")}%'")
              dbConnection.createStatement().executeQuery(query)
            } match {
              case Failure(_) => Future(Nil) pipeTo sender()

              case Success(resultSet: ResultSet) =>
                Future(getRecommendationReqModel.dbModelTransformer.get.buildResult(resultSet)) pipeTo sender()
            }
          } else {
            Future(Nil) pipeTo sender()
          }
      }
  }
}

object RatingRequestProcessorActor {
  def props(connection: Connection): Props = Props(new RatingRequestProcessorActor(connection))
}
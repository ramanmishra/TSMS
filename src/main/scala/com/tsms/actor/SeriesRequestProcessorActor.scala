package com.tsms.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern._
import com.tsms.models.series.{AddSeriesReqModel, DeleteSeriesReqModel, GetAllSeriesReqModel, GetSeriesReqModel}

import java.sql.{Connection, ResultSet}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * This actor takes care of all the Series Related request
 *
 * @param dbConnection database connection to execute queries
 */
class SeriesRequestProcessorActor(dbConnection: Connection) extends Actor with ActorLogging {
  override def receive: Receive = {
    case addSeriesReqModel: AddSeriesReqModel =>
      log.info(s"Processing $addSeriesReqModel")

      Try {
        dbConnection.createStatement().executeUpdate(addSeriesReqModel.query)
      } match {
        case Failure(_) => Future(false) pipeTo sender()

        case Success(_) => Future(true) pipeTo sender
      }

    case deleteSeriesReqModel: DeleteSeriesReqModel =>
      log.info(s"Processing $deleteSeriesReqModel")

      Try {
        dbConnection.createStatement().executeQuery(deleteSeriesReqModel.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender

        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(deleteSeriesReqModel.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) => Future(true) pipeTo sender
          }
        } else {
          Future(false) pipeTo sender
        }
      }

    case getAllSeriesReqModel: GetAllSeriesReqModel =>
      log.info(s"Processing $getAllSeriesReqModel")

      Try {
        dbConnection.createStatement().executeQuery(getAllSeriesReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          val users = getAllSeriesReqModel.dbModelTransformer.get.buildResult(resultSet)
          Future(users) pipeTo sender()
      }

    case getSeriesReqModel: GetSeriesReqModel =>
      log.info(s"Processing $getSeriesReqModel")

      Try {
        dbConnection.createStatement().executeQuery(getSeriesReqModel.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet: ResultSet) =>
          val user = getSeriesReqModel.dbModelTransformer.get.buildResult(resultSet)

          Future(user) pipeTo sender()
      }
  }
}

object SeriesRequestProcessorActor {
  def props(connection: Connection) = Props(new SeriesRequestProcessorActor(connection))
}
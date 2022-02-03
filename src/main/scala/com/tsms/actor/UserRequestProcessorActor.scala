package com.tsms.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern._
import com.tsms.models.user._

import java.sql.{Connection, ResultSet, Statement}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * This actor takes care of all the User Related request
 *
 * @param dbConnection database connection to execute queries
 */
class UserRequestProcessorActor(dbConnection: Connection) extends Actor with UserActorUtil with ActorLogging {
  def receive: Receive = {
    case createUserReqModel: CreateUserReqModel =>
      log.info(s"Processing $createUserReqModel")

      val pstmt = dbConnection.prepareStatement(createUserReqModel.query, Statement.RETURN_GENERATED_KEYS)
      setValues(createUserReqModel, pstmt)

      Try {
        pstmt.executeUpdate()
      } match {
        case Failure(_) => Future(false) pipeTo sender()

        case Success(_) => Future(true) pipeTo sender
      }

    case getAllUser: GetAllUsersReqModel =>
      log.info(s"Processing $getAllUser")
      Try {
        dbConnection.createStatement().executeQuery(getAllUser.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet) =>
          val users = getAllUser.dbModelTransformer.get.buildResult(resultSet)
          Future(users) pipeTo sender()
      }

    case getUserReq: GetUserReqModel =>
      log.info(s"processing $getUserReq")
      Try {
        dbConnection.createStatement().executeQuery(getUserReq.query)
      } match {
        case Failure(_) => Future(Nil) pipeTo sender()

        case Success(resultSet: ResultSet) =>
          val user = getUserReq.dbModelTransformer.get.buildResult(resultSet)

          Future(user) pipeTo sender()
      }

    case updateUserReq: UpdateUserReqModel =>
      log.info(s"Processing $updateUserReq")
      Try {
        dbConnection.createStatement().executeQuery(updateUserReq.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender

        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(updateUserReq.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) => Future(true) pipeTo sender
          }
        } else {
          Future(false) pipeTo sender
        }
      }

    case deleteUserReq: DeleteUserReqModel =>
      log.info(s"Processing $deleteUserReq")
      Try {
        dbConnection.createStatement().executeQuery(deleteUserReq.selectQuery.get)
      } match {
        case Failure(_) => Future(false) pipeTo sender

        case Success(value) => if (value.next()) {
          Try {
            dbConnection.createStatement().executeUpdate(deleteUserReq.query)
          } match {
            case Failure(_) => Future(false) pipeTo sender

            case Success(_) => Future(true) pipeTo sender
          }
        } else {
          Future(false) pipeTo sender
        }
      }

  }
}

object UserRequestProcessorActor {
  def props(connection: Connection): Props = Props(new UserRequestProcessorActor(connection))
}
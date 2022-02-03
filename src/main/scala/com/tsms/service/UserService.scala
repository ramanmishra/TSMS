package com.tsms.service

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.tsms.models.user._
import com.tsms.utils.JsonSupport
import com.typesafe.config.Config

import scala.util.{Failure, Success}

/**
 * This service provides all the User related endpoints
 *
 * @param supervisorActor supervisor actor of the application
 * @param timeout         ask timeout
 * @param actorSystem     actor system in which actor is being created
 * @param config          configuration from the application.conf file
 */
class UserService(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                             actorSystem: ActorSystem,
                                             config: Config) extends JsonSupport {
  val userServiceRoutes: Route =
    concat(
      path("getUsers") {
        get {
          parameter("userId".as[Int]) { userId =>
            onComplete((supervisorActor ? GetAllUsersReqModel(userId)).mapTo[List[UserResponseModel]]) {
              case Success(users) =>
                users match {
                  case Nil => complete("No User Found")

                  case _ => complete(users)
                }

              case Failure(ex) => complete(ex.getMessage)
            }
          }
        }
      },
      path("getUser") {
        get {
          parameter("userId".as[Int]) { userId =>
            onComplete((supervisorActor ? GetUserReqModel(userId)).mapTo[List[UserResponseModel]]) {
              case Success(user) =>
                user match {
                  case Nil => complete("User Not Found")

                  case _ => complete(user.head)
                }

              case Failure(ex) => complete(ex.getMessage)
            }
          }
        }
      },
      path("addUser") {
        post {
          decodeRequest {
            entity(as[CreateUserReqModel]) { createUserReq: CreateUserReqModel =>
              onComplete((supervisorActor ? createUserReq).mapTo[Boolean]) {
                case Success(value) =>
                  if (value) {
                    complete(s"User Created ${createUserReq.firstName}")
                  } else {
                    complete(s"Couldn't create the user Please try again!!")
                  }

                case Failure(_) => complete(StatusCode.int2StatusCode(500))
              }
            }
          }
        }
      },
      path("deleteUser") {
        delete {
          parameter("userId".as[Int]) { userId =>
            onComplete((supervisorActor ? DeleteUserReqModel(userId)).mapTo[Boolean]) {
              case Success(value) =>
                if (value) {
                  complete(s"User Deleted with $userId")
                } else {
                  complete(s"Couldn't delete the user Make sure user exists and Please try again!!")
                }

              case Failure(_) => complete(StatusCode.int2StatusCode(500))
            }
          }
        }
      },
      path("updateUser") {
        put {
          decodeRequest {
            entity(as[UpdateUserReqModel]) { updateUserReq =>
              onComplete((supervisorActor ? updateUserReq).mapTo[Boolean]) {
                case Success(value) =>
                  if (value) {
                    complete(s"User Details updated Successfully")
                  } else {
                    complete(s"Couldn't update the user Make sure user exists Please try again!!")
                  }

                case Failure(_) => complete(StatusCode.int2StatusCode(500))
              }
            }
          }
        }
      }
    )
}

object UserService {
  def apply(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                       actorSystem: ActorSystem,
                                       config: Config): Route = new UserService(supervisorActor).userServiceRoutes
}
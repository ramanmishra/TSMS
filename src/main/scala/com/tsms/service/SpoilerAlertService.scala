package com.tsms.service

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern._
import akka.util.Timeout
import com.tsms.models.spoileralert.GetSpoilerAlertReqModel
import com.tsms.utils.JsonSupport
import com.typesafe.config.Config

import scala.util.{Failure, Success}

/**
 * This service provides all the Spoiler Alert related endpoints
 *
 * @param supervisorActor supervisor actor of the application
 * @param timeout         ask timeout
 * @param actorSystem     actor system in which actor is being created
 * @param config          configuration from the application.conf file
 */
class SpoilerAlertService(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                                     actorSystem: ActorSystem,
                                                     config: Config) extends JsonSupport {
  val routes: Route = concat(
    path("getSpoilerAlert") {
      get {
        parameters("userId1".as[Int], "userId2".as[Int], "episodeId".as[String]) { (userId1, userId2, episodeId) =>
          onComplete((supervisorActor ? GetSpoilerAlertReqModel(userId1, userId2, episodeId)).mapTo[String]) {
            case Success(alert) => complete(alert)

            case Failure(ex) => complete(ex.getMessage)
          }
        }
      }
    }
  )
}

object SpoilerAlertService {
  def apply(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                       actorSystem: ActorSystem,
                                       config: Config): Route = new SpoilerAlertService(supervisorActor).routes


}
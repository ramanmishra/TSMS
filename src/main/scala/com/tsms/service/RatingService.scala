package com.tsms.service

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern._
import akka.util.Timeout
import com.tsms.models.rating._
import com.tsms.utils.JsonSupport
import com.typesafe.config.Config

import scala.util.{Failure, Success}

/**
 * This service provides all the rating related endpoints
 *
 * @param supervisorActor supervisor actor of the application
 * @param timeout         ask timeout
 * @param actorSystem     actor system in which actor is being created
 * @param config          configuration from the application.conf file
 */
class RatingService(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                               actorSystem: ActorSystem,
                                               config: Config) extends JsonSupport {

  val routes: Route = concat(
    path("getSeriesRating") {
      get {
        parameter("userId".as[Int], "seriesId".as[Int]) { (userId, seriesId) =>
          onComplete((supervisorActor ? GetSeriesRatingReqModel(userId, seriesId)).mapTo[String]) {
            case Success(rating) => complete(rating)

            case Failure(_) => complete(StatusCode.int2StatusCode(500))
          }
        }
      }
    },
    path("updateSeriesRating") {
      post {
        decodeRequest {
          entity(as[UpdateSeriesRatingReqModel]) { updateSeriesRatingReq =>
            onComplete((supervisorActor ? updateSeriesRatingReq).mapTo[Boolean]) {
              case Success(value) =>
                if (value) {
                  complete(s"Series Rating Updated")
                } else {
                  complete("Couldn't rate the series Make sure user and series exists")
                }

              case Failure(_) => complete(StatusCode.int2StatusCode(500))
            }
          }
        }
      }
    },
    path("getSeasonRating") {
      get {
        parameter("userId".as[Int], "seasonId".as[String]) { (userId, seasonId) =>
          onComplete((supervisorActor ? GetSeasonRatingReqModel(userId, seasonId)).mapTo[String]) {
            case Success(rating) => complete(rating)

            case Failure(_) => complete(StatusCode.int2StatusCode(500))
          }
        }
      }
    },
    path("updateSeasonRating") {
      post {
        decodeRequest {
          entity(as[UpdateSeasonRatingReqModel]) { updateSeasonRatingReq =>
            onComplete((supervisorActor ? updateSeasonRatingReq).mapTo[Boolean]) {
              case Success(value) =>
                if (value) {
                  complete(s"Season Rating Updated")
                } else {
                  complete("Couldn't rate the Season Make sure user and Season exists")
                }

              case Failure(_) => complete(StatusCode.int2StatusCode(500))
            }
          }
        }
      }
    },
    path("getEpisodeRating") {
      get {
        parameter("userId".as[Int], "episodeId".as[String]) { (userId, episodeId) =>
          onComplete((supervisorActor ? GetEpisodeRatingReqModel(userId, episodeId)).mapTo[String]) {
            case Success(rating) => complete(rating)

            case Failure(_) => complete(StatusCode.int2StatusCode(500))
          }
        }
      }
    },
    path("updateEpisodeRating") {
      post {
        decodeRequest {
          entity(as[UpdateEpisodeRatingReqModel]) { updateEpisodeRatingReq =>
            onComplete((supervisorActor ? updateEpisodeRatingReq).mapTo[Boolean]) {
              case Success(value) =>
                if (value) {
                  complete(s"Episode Rating Updated")
                } else {
                  complete("Couldn't rate the Episode Make sure user and Episode exists")
                }

              case Failure(_) => complete(StatusCode.int2StatusCode(500))
            }
          }
        }
      }
    },
    path("getSeriesRecommendation") {
      get {
        parameter("userId".as[Int]) { userId =>
          onComplete((supervisorActor ? GetRecommendationReqModel(userId)).mapTo[List[RecommendationResponseModel]]) {
            case Success(recommendation) => complete(recommendation)

            case Failure(_) => complete(StatusCode.int2StatusCode(500))
          }
        }
      }
    }
  )
}

object RatingService {
  def apply(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                       actorSystem: ActorSystem,
                                       config: Config): Route = new RatingService(supervisorActor).routes
}
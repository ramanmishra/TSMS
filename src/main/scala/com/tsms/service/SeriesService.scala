package com.tsms.service

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern._
import akka.util.Timeout
import com.tsms.models.episode._
import com.tsms.models.season.{AddSeasonReqModel, DeleteSeasonReqModel, GetSeasonsReqModel, SeasonResponseModel}
import com.tsms.models.series._
import com.tsms.utils.JsonSupport
import com.typesafe.config.Config

import scala.util.{Failure, Success}

/**
 * This service provides all the Series related endpoints
 *
 * @param supervisorActor supervisor actor of the application
 * @param timeout         ask timeout
 * @param actorSystem     actor system in which actor is being created
 * @param config          configuration from the application.conf file
 */
class SeriesService(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                               actorSystem: ActorSystem,
                                               config: Config) extends JsonSupport {
  val seriesServiceRoutes: Route =
    concat(
      path("addSeries") {
        post {
          decodeRequest {
            entity(as[AddSeriesReqModel]) { addSeriesReq =>
              onComplete((supervisorActor ? addSeriesReq).mapTo[Boolean]) {
                case Success(value) =>
                  if (value) {
                    complete(s"Series Added: ${addSeriesReq.seriesName}")
                  } else {
                    complete(s"Couldn't add the series Please try again!!")
                  }
                case Failure(_) => complete(StatusCode.int2StatusCode(500))
              }
            }
          }
        }
      },
      path("deleteSeries") {
        delete {
          parameter("seriesId".as[Int]) { seriesId =>
            onComplete((supervisorActor ? DeleteSeriesReqModel(seriesId)).mapTo[Boolean]) {
              case Success(value) =>
                if (value) {
                  complete(s"Series Deleted with $seriesId")
                } else {
                  complete(s"Couldn't delete the series Make sure series exists and Please try again!!")
                }

              case Failure(_) => complete(StatusCode.int2StatusCode(500))
            }
          }
        }
      },
      path("getAllSeries") {
        get {
          parameter("seriesId".as[Int]) { seriesId =>
            onComplete((supervisorActor ? GetAllSeriesReqModel(seriesId)).mapTo[List[SeriesResponseModel]]) {
              case Success(series) =>
                series match {
                  case Nil => complete("No Series Found")

                  case _ => complete(series)
                }

              case Failure(ex) => complete(ex.getMessage)
            }
          }
        }
      },
      path("getSeries") {
        get {
          parameter("seriesId".as[Int]) { seriesId =>
            onComplete((supervisorActor ? GetSeriesReqModel(seriesId)).mapTo[List[SeriesResponseModel]]) {
              case Success(series) =>
                series match {
                  case Nil => complete("Series Not Found")

                  case _ => complete(series.head)
                }

              case Failure(ex) => complete(ex.getMessage)
            }
          }
        }
      },
      path("addSeason") {
        post {
          decodeRequest {
            entity(as[AddSeasonReqModel]) { addSeasonReq =>
              onComplete((supervisorActor ? addSeasonReq).mapTo[Boolean]) {
                case Success(value) =>
                  if (value) {
                    complete(s"Season Added: ${addSeasonReq.seasonName}")
                  } else {
                    complete(s"Couldn't add the Season Please try again!!")
                  }
                case Failure(_) => complete(StatusCode.int2StatusCode(500))
              }
            }
          }
        }
      },
      path("deleteSeason") {
        delete {
          parameter("seriesId".as[Int], "seasonName".as[String]) { (seriesId, seasonName) =>
            onComplete((supervisorActor ? DeleteSeasonReqModel(seriesId, seasonName)).mapTo[Boolean]) {
              case Success(value) =>
                if (value) {
                  complete(s"Season Deleted: $seriesId-$seasonName")
                } else {
                  complete(s"Couldn't delete the Season Make sure Season exists and Please try again!!")
                }

              case Failure(_) => complete(StatusCode.int2StatusCode(500))
            }
          }
        }
      },
      path("getSeasons") {
        get {
          parameter("seriesId".as[Int]) { seriesId =>
            onComplete((supervisorActor ? GetSeasonsReqModel(seriesId)).mapTo[List[SeasonResponseModel]]) {
              case Success(seasons) =>
                seasons match {
                  case Nil => complete("Seasons Not Found")

                  case _ => complete(seasons)
                }

              case Failure(ex) => complete(ex.getMessage)
            }
          }
        }
      },
      path("addEpisode") {
        post {
          decodeRequest {
            entity(as[AddEpisodeReqModel]) { addEpisodeReq =>
              onComplete((supervisorActor ? addEpisodeReq).mapTo[Boolean]) {
                case Success(value) =>
                  if (value) {
                    complete(s"Episode Added: ${addEpisodeReq.episodeName}")
                  } else {
                    complete(s"Couldn't add the Episode Make sure Season and series Exists Please try again!!")
                  }
                case Failure(_) => complete(StatusCode.int2StatusCode(500))
              }
            }
          }
        }
      },
      path("deleteEpisode") {
        delete {
          parameter("seasonId".as[String], "episodeName".as[String]) { (seasonId, episodeName) =>
            onComplete((supervisorActor ? DeleteEpisodeReqModel(seasonId, episodeName)).mapTo[Boolean]) {
              case Success(value) =>
                if (value) {
                  complete(s"Episode Deleted: $seasonId-$episodeName")
                } else {
                  complete(s"Couldn't delete the Season Make sure Season exists and Please try again!!")
                }

              case Failure(_) => complete(StatusCode.int2StatusCode(500))
            }
          }
        }
      },
      path("getEpisodes") {
        get {
          parameter("seasonId".as[String]) { seasonId =>
            onComplete((supervisorActor ? GetEpisodesReqModel(seasonId)).mapTo[List[EpisodeResponseModel]]) {
              case Success(episodes) =>
                episodes match {
                  case Nil => complete("Episodes Not Found")

                  case _ => complete(episodes)
                }

              case Failure(ex) => complete(ex.getMessage)
            }
          }
        }
      },
      path("getWatchStatus") {
        get {
          parameter("userId".as[Int], "episodeId".as[String]) { (userId, episodeId) =>
            onComplete((supervisorActor ? GetWatchStatusReqModel(userId, episodeId)).mapTo[List[WatchStatusResponseModel]]) {
              case Success(watchStatus) =>
                watchStatus match {
                  case Nil => complete("Didn't Watch Yet")

                  case _ => complete(watchStatus.head)
                }

              case Failure(ex) => complete(ex.getMessage)
            }
          }
        }
      },
      path("updateWatchStatus") {
        post {
          decodeRequest {
            entity(as[UpdateWatchStatusReqModel]) { updateWatchStatusReq =>
              onComplete((supervisorActor ? updateWatchStatusReq).mapTo[Boolean]) {
                case Success(value) =>
                  if (value) {
                    complete(s"Watch Status Updated: ${updateWatchStatusReq.episodeId}")
                  } else {
                    complete(s"Couldn't update watch Status Make sure user, series, season and episode exists Please try again!!")
                  }
                case Failure(_) => complete(StatusCode.int2StatusCode(500))
              }
            }
          }
        }
      }
    )
}

object SeriesService {
  def apply(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                       actorSystem: ActorSystem,
                                       config: Config): Route =
    new SeriesService(supervisorActor).seriesServiceRoutes
}
package com.tsms.actor


import akka.actor.{Actor, ActorLogging, Props}
import com.tsms.models.episode.{BaseEpisodeRequest, BaseWatchStatusRequest}
import com.tsms.models.rating.{BaseRatingRequest, GetRecommendationReqModel}
import com.tsms.models.season.BaseSeasonRequest
import com.tsms.models.series.BaseSeriesRequest
import com.tsms.models.spoileralert.GetSpoilerAlertReqModel
import com.tsms.models.user.BaseUserRequest

import java.sql.Connection

/**
 * This class provides supervisor actor for each child actor
 *
 * @param dbConnection database connection to execute queries
 */
class AppSupervisorActor(dbConnection: Connection) extends Actor with ActorLogging {
  private val userRequestProcessorActor = context.actorOf(UserRequestProcessorActor.props(dbConnection), "userRequestProcessorActor")
  private val seriesRequestProcessorActor = context.actorOf(SeriesRequestProcessorActor.props(dbConnection), "seriesRequestProcessorActor")
  private val seasonRequestProcessorActor = context.actorOf(SeasonRequestProcessorActor.props(dbConnection), "seasonRequestProcessorActor")
  private val episodeRequestProcessorActor = context.actorOf(EpisodeRequestProcessorActor.props(dbConnection), "episodeRequestProcessorActor")
  private val watchStatusRequestProcessorActor = context.actorOf(WatchStatusRequestProcessorActor.props(dbConnection), "watchStatusRequestProcessorActor")
  private val spoilerAlertRequestProcessorActor = context.actorOf(SpoilerAlertRequestProcessorActor.props(dbConnection), "spoilerAlertRequestProcessorActor")
  private val ratingRequestProcessorActor = context.actorOf(RatingRequestProcessorActor.props(dbConnection), "ratingRequestProcessorActor")

  context.watch(userRequestProcessorActor)
  context.watch(seriesRequestProcessorActor)
  context.watch(seasonRequestProcessorActor)
  context.watch(episodeRequestProcessorActor)
  context.watch(watchStatusRequestProcessorActor)
  context.watch(spoilerAlertRequestProcessorActor)
  context.watch(ratingRequestProcessorActor)

  override def receive: Receive = {
    case userRequest: BaseUserRequest =>
      userRequestProcessorActor forward userRequest

    case seriesRequest: BaseSeriesRequest =>
      seriesRequestProcessorActor forward seriesRequest

    case seasonRequest: BaseSeasonRequest =>
      seasonRequestProcessorActor forward seasonRequest

    case episodeRequest: BaseEpisodeRequest =>
      episodeRequestProcessorActor forward episodeRequest

    case watchStatusRequest: BaseWatchStatusRequest =>
      watchStatusRequestProcessorActor forward watchStatusRequest

    case spoilerAlertReqModel: GetSpoilerAlertReqModel =>
      spoilerAlertRequestProcessorActor forward spoilerAlertReqModel

    case ratingReqModel: BaseRatingRequest =>
      ratingRequestProcessorActor forward ratingReqModel

    case getRecommendationReqModel: GetRecommendationReqModel =>
      ratingRequestProcessorActor forward getRecommendationReqModel
  }
}


object AppSupervisorActor {
  def props(connection: Connection): Props = Props(new AppSupervisorActor(connection))
}
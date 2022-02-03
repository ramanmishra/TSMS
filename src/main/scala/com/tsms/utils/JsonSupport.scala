package com.tsms.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.tsms.models.episode.{AddEpisodeReqModel, EpisodeResponseModel, UpdateWatchStatusReqModel, WatchStatusResponseModel}
import com.tsms.models.rating.{RecommendationResponseModel, UpdateEpisodeRatingReqModel, UpdateSeasonRatingReqModel, UpdateSeriesRatingReqModel}
import com.tsms.models.season.{AddSeasonReqModel, SeasonResponseModel}
import com.tsms.models.series.{AddSeriesReqModel, SeriesResponseModel}
import com.tsms.models.user.{CreateUserReqModel, UpdateUserReqModel, UserResponseModel}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
 * Provides marshaller and unmarshaller for the request and response
 */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val updateUserReqFormat: RootJsonFormat[UpdateUserReqModel] = jsonFormat9(UpdateUserReqModel)
  implicit val userResponseModelFormat: RootJsonFormat[UserResponseModel] = jsonFormat9(UserResponseModel)
  implicit val createUserReqFormat: RootJsonFormat[CreateUserReqModel] = jsonFormat8(CreateUserReqModel)

  implicit val seriesResponseModelFormat: RootJsonFormat[SeriesResponseModel] = jsonFormat6(SeriesResponseModel)
  implicit val addSeriesReqModelFormat: RootJsonFormat[AddSeriesReqModel] = jsonFormat5(AddSeriesReqModel)

  implicit val seasonResponseModelFormat: RootJsonFormat[SeasonResponseModel] = jsonFormat6(SeasonResponseModel)
  implicit val addSeasonReqModelFormat: RootJsonFormat[AddSeasonReqModel] = jsonFormat6(AddSeasonReqModel)

  implicit val episodeResponseModelFormat: RootJsonFormat[EpisodeResponseModel] = jsonFormat7(EpisodeResponseModel)
  implicit val addEpisodeReqModelFormat: RootJsonFormat[AddEpisodeReqModel] = jsonFormat7(AddEpisodeReqModel)

  implicit val watchStatusResponseModelFormat: RootJsonFormat[WatchStatusResponseModel] = jsonFormat2(WatchStatusResponseModel)
  implicit val updateWatchStatusReqModelFormat: RootJsonFormat[UpdateWatchStatusReqModel] = jsonFormat4(UpdateWatchStatusReqModel)

  implicit val updateSeriesRatingReqModelFormat: RootJsonFormat[UpdateSeriesRatingReqModel] = jsonFormat3(UpdateSeriesRatingReqModel)
  implicit val updateSeasonRatingReqModelFormat: RootJsonFormat[UpdateSeasonRatingReqModel] = jsonFormat3(UpdateSeasonRatingReqModel)
  implicit val updateEpisodeRatingReqModelFormat: RootJsonFormat[UpdateEpisodeRatingReqModel] = jsonFormat3(UpdateEpisodeRatingReqModel)
  implicit val recommendationResponseModelFormat: RootJsonFormat[RecommendationResponseModel] = jsonFormat3(RecommendationResponseModel)
}

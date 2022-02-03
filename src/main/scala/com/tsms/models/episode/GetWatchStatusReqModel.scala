package com.tsms.models.episode

import com.tsms.constants.TableConstants.WATCH_STATUS_TABLE_NAME
import com.tsms.models.DatabaseQueryModel
import com.tsms.transformer.{DBModelTransformer, WatchStatusResponseModelTransformer}

/**
 * This data model is used to take request to get the watch status of an user for an episode
 *
 * @param userId    user Id which is watching/watched the episode
 * @param episodeId episode id which is being watched
 */
case class GetWatchStatusReqModel(userId: Int,
                                  episodeId: String) extends DatabaseQueryModel with BaseWatchStatusRequest {
  def getSeasonAndSeriesId: (Int, String) = {
    val values = episodeId.split("-").toList
    val seriesId = values.head
    val seasonId = values(1)
    (seriesId.toInt, s"$seriesId-$seasonId")
  }

  override def tableName: String = WATCH_STATUS_TABLE_NAME

  override def query: String = s"SELECT watched_till, status FROM $tableName WHERE user_id =$userId AND series_id=${getSeasonAndSeriesId._1} AND season_id = '${getSeasonAndSeriesId._2}' AND episode_id='$episodeId'"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(WatchStatusResponseModelTransformer)
}

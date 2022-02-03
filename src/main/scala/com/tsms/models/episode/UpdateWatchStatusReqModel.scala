package com.tsms.models.episode

import com.tsms.constants.TableConstants.WATCH_STATUS_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data model is being used to take request to update the watching status of an episode
 *
 * @param userId      user id who is watching the episode
 * @param episodeId   episode id which is being watched
 * @param watchedTill second which user already watched
 * @param status      status of watch e.g. watching/watched
 */
case class UpdateWatchStatusReqModel(
                                      userId: Int,
                                      episodeId: String,
                                      watchedTill: Int,
                                      status: String
                                    ) extends DatabaseQueryModel with BaseWatchStatusRequest {
  def getSeasonAndSeriesId: (Int, String) = {
    val values = episodeId.split("-").toList
    val seriesId = values.head
    val seasonId = values(1)
    (seriesId.toInt, s"$seriesId-$seasonId")
  }

  override def tableName: String = WATCH_STATUS_TABLE_NAME

  override def query: String = s"INSERT INTO $tableName(user_id, series_id, season_id, episode_id, watched_till, status) VALUES ($userId, ${getSeasonAndSeriesId._1}, '${getSeasonAndSeriesId._2}', '$episodeId', $watchedTill, '$status');"

  override def selectQuery: Option[String] = Some(s"SELECT user_id, series_id, season_id, episode_id, watched_till, status from $tableName WHERE user_id = $userId AND series_id = ${getSeasonAndSeriesId._1} AND season_id = '${getSeasonAndSeriesId._2}' AND episode_id = '$episodeId'")

  override def updateQuery: Option[String] = Some(s"UPDATE $tableName SET watched_till = $watchedTill, status = '$status' WHERE user_id = $userId AND series_id = ${getSeasonAndSeriesId._1} AND season_id = '${getSeasonAndSeriesId._2}' AND episode_id = '$episodeId'")
}

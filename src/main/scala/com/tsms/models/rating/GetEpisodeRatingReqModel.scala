package com.tsms.models.rating

import com.tsms.constants.TableConstants.EPISODE_RATING_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data model is used to take request to get the episode rating for given by a user
 *
 * @param episodeId episode Id in which is being rated
 */
case class GetEpisodeRatingReqModel(userId: Int,
                                    episodeId: String) extends DatabaseQueryModel with BaseRatingRequest {
  override def tableName: String = EPISODE_RATING_TABLE_NAME

  override def query: String = s"SELECT rating FROM $tableName where user_id = $userId AND episode_id = '$episodeId'"
}

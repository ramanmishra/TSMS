package com.tsms.models.rating

import com.tsms.constants.TableConstants.SEASON_RATING_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data model is used to take request to get the season rating for given by a user
 *
 * @param seasonId season Id in which is being rated
 */
case class GetSeasonRatingReqModel(userId: Int,
                                   seasonId: String) extends DatabaseQueryModel with BaseRatingRequest {
  override def tableName: String = SEASON_RATING_TABLE_NAME

  override def query: String = s"SELECT rating FROM $tableName where user_id = $userId AND season_id = '$seasonId'"
}

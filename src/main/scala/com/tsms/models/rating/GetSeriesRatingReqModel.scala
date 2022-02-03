package com.tsms.models.rating

import com.tsms.constants.TableConstants.SERIES_RATING_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data model is used to take request to get the series rating for given by a user
 *
 * @param seriesId series Id in which is being rated
 */
case class GetSeriesRatingReqModel(userId: Int,
                                   seriesId: Int) extends DatabaseQueryModel with BaseRatingRequest {
  override def tableName: String = SERIES_RATING_TABLE_NAME

  override def query: String = s"SELECT rating FROM $tableName where user_id = $userId AND series_id = $seriesId"
}

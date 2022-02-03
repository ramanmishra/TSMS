package com.tsms.models.rating

import com.tsms.constants.TableConstants.{SERIES_RATING_TABLE_NAME, SERIES_TABLE_NAME}
import com.tsms.models.DatabaseQueryModel

/**
 * This data Model is being used to take request to update an series rating given by an user
 *
 * @param userId   user if who is updating the rating
 * @param seriesId series id of the series which is being rated
 * @param rating   rating to be update
 */
case class UpdateSeriesRatingReqModel(userId: Int,
                                      seriesId: Int,
                                      rating: Float) extends DatabaseQueryModel with BaseRatingRequest {

  require(rating <= 5.0, "Rating should be less or equal to 5.0")

  override def tableName: String = SERIES_RATING_TABLE_NAME

  override def query: String = s"INSERT INTO $tableName(user_id, series_id, rating) VALUES ($userId, $seriesId, $rating);"

  override def selectQuery: Option[String] = Some(s"SELECT user_id, series_id from $tableName WHERE user_id = $userId ANd series_id = $seriesId")

  override def updateQuery: Option[String] = Some(s"UPDATE $tableName SET rating = $rating WHERE user_id = $userId AND series_id = $seriesId")

  override def otherTableUpdateQuery: Option[String] = Some(s"UPDATE $SERIES_TABLE_NAME SET rating = (select avg(rating) as rating from $tableName where series_id = $seriesId) WHERE series_id = '$seriesId'")
}

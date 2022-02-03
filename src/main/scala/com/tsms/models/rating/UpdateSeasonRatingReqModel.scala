package com.tsms.models.rating

import com.tsms.constants.TableConstants.{SEASON_RATING_TABLE_NAME, SEASON_TABLE_NAME}
import com.tsms.models.DatabaseQueryModel

/**
 * This data Model is being used to take request to update an season rating given by an user
 *
 * @param userId   user if who is updating the rating
 * @param seasonId season id of the season which is being rated
 * @param rating   rating to be update
 */
case class UpdateSeasonRatingReqModel(userId: Int,
                                      seasonId: String,
                                      rating: Float) extends DatabaseQueryModel with BaseRatingRequest {

  require(rating <= 5.0, "Rating should be less or equal to 5.0")

  override def tableName: String = SEASON_RATING_TABLE_NAME

  override def query: String = s"INSERT INTO $tableName(user_id, season_id, rating) VALUES ($userId, '$seasonId', $rating);"

  override def selectQuery: Option[String] = Some(s"SELECT user_id, season_id from $tableName WHERE user_id = $userId ANd season_id = '$seasonId'")

  override def updateQuery: Option[String] = Some(s"UPDATE $tableName SET rating = $rating WHERE user_id = $userId AND season_id = '$seasonId'")

  override def otherTableUpdateQuery: Option[String] = Some(s"UPDATE $SEASON_TABLE_NAME SET rating = (select avg(rating) as rating from $tableName where season_id = '$seasonId') WHERE season_id = '$seasonId'")
}

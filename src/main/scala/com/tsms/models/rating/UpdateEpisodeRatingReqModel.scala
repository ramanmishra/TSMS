package com.tsms.models.rating

import com.tsms.constants.TableConstants.{EPISODE_RATING_TABLE_NAME, EPISODE_TABLE_NAME}
import com.tsms.models.DatabaseQueryModel

/**
 * This data Model is being used to take request to update an episode rating given by an user
 *
 * @param userId    user if who is updating the rating
 * @param episodeId episode id of the episode which is being rated
 * @param rating    rating to be update
 */
case class UpdateEpisodeRatingReqModel(userId: Int,
                                       episodeId: String,
                                       rating: Float) extends DatabaseQueryModel with BaseRatingRequest {
  require(rating <= 5.0, "Rating should be less or equal to 5.0")

  override def tableName: String = EPISODE_RATING_TABLE_NAME

  override def query: String = s"INSERT INTO $tableName(user_id, episode_id, rating) VALUES ($userId, '$episodeId', $rating);"

  override def selectQuery: Option[String] = Some(s"SELECT user_id, episode_id from $tableName WHERE user_id = $userId ANd episode_id = '$episodeId'")

  override def updateQuery: Option[String] = Some(s"UPDATE $tableName SET rating = $rating WHERE user_id = $userId AND episode_id = '$episodeId'")

  override def otherTableUpdateQuery: Option[String] = Some(s"UPDATE $EPISODE_TABLE_NAME SET rating = (select avg(rating) as rating from $tableName where episode_id = '$episodeId') WHERE episode_id = '$episodeId'")
}

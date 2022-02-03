package com.tsms.models.episode

import com.tsms.constants.TableConstants.EPISODE_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data model is used to take request to add a new episode in a season
 *
 * @param seasonId      season Id in which episode supposed to be added
 * @param episodeName   name of the episode being added
 * @param duration      length of the episode in second
 * @param genre         genre of the episode
 * @param rating        rating of the episode
 * @param casting       casting of the episode
 * @param agePreference age limit for which episode is preferred
 */
case class AddEpisodeReqModel(
                               seasonId: String,
                               episodeName: String,
                               duration: Int,
                               genre: String,
                               rating: Float,
                               casting: String,
                               agePreference: Int
                             ) extends DatabaseQueryModel with BaseEpisodeRequest {
  require(rating <= 5.0, "Rating should be less or equal to 5.0")

  def createEpisodeId = s"$seasonId-$episodeName"

  override def tableName: String = EPISODE_TABLE_NAME

  override def query: String = s"INSERT INTO $tableName(episode_id, season_id, episode_name, duration, genre, rating, casting, age_preference) VALUES ('$createEpisodeId', '$seasonId','$episodeName', $duration, '$genre', $rating, '$casting', $agePreference);"
}
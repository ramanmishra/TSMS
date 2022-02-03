package com.tsms.models.season

import com.tsms.constants.TableConstants.SEASON_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data Model is being used to take request to add a new season in a series
 *
 * @param seriesId      series id in which the season suppose to be added
 * @param seasonName    name of the season
 * @param genre         genre of the season
 * @param rating        average rating given by all the users
 * @param casting       casting in the season
 * @param agePreference age limit preferred to watch the season
 */
case class AddSeasonReqModel(
                              seriesId: Int,
                              seasonName: String,
                              genre: String,
                              rating: Float,
                              casting: String,
                              agePreference: Int
                            ) extends DatabaseQueryModel with BaseSeasonRequest {
  require(rating <= 5.0, "Rating should be less or equal to 5.0")

  def createSeasonId: String = s"$seriesId-$seasonName"

  override def tableName: String = SEASON_TABLE_NAME

  override def query: String = s"INSERT INTO $tableName(season_id, series_id, season_name, genre, rating, casting, age_preference)VALUES ('$createSeasonId', $seriesId, '$seasonName', '$genre', $rating, '$casting', $agePreference);"
}
package com.tsms.models.series

import com.tsms.constants.TableConstants.SERIES_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data model is being used to get the request to add a new series into the system
 *
 * @param seriesName    name of the series which is getting added
 * @param genre         genre of the series which is getting added
 * @param rating        rating of the series
 * @param casting       casting of the series it's a comma separated names of the actors
 * @param agePreference age preference which is used to recommend if certain person should watch this series
 */
case class AddSeriesReqModel(
                              seriesName: String,
                              genre: String,
                              rating: Float,
                              casting: String,
                              agePreference: Int
                            ) extends DatabaseQueryModel with BaseSeriesRequest {
  require(rating <= 5.0, "Rating should be less or equal to 5.0")

  override def tableName: String = SERIES_TABLE_NAME

  override def query: String = s"INSERT INTO $tableName(series_name, genre, rating, casting, age_preference)  VALUES ('$seriesName', '$genre', $rating, '$casting', $agePreference);"
}

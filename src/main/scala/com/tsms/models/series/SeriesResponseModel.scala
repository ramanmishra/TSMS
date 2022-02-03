package com.tsms.models.series

import com.tsms.models.BaseModel

/**
 * This data model is being used to fetch and create an object of series table row
 *
 * @param seriesId      series Id of a series
 * @param seriesName    name of the series
 * @param genre         genre in which series belongs
 * @param rating        rating of the series
 * @param casting       casting in the series comma separated values
 * @param agePreference age preference of viewers who are recommended to watch the series
 */
case class SeriesResponseModel(
                        seriesId: Int,
                        seriesName: String,
                        genre: String,
                        rating: Float,
                        casting: String,
                        agePreference: Int
                      ) extends BaseModel

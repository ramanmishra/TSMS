package com.tsms.models.season

import com.tsms.models.BaseModel

/**
 * This data model is used as the response to get season requests
 *
 * @param season_id      season Id of the fetched season
 * @param season_name    name of the season
 * @param genre          genre of the season
 * @param rating         average rating of the season given by all the users
 * @param casting        casting of the season
 * @param age_preference age limit who are preferred to watch the season
 */
case class SeasonResponseModel(
                                season_id: String,
                                season_name: String,
                                genre: String,
                                rating: Float,
                                casting: String,
                                age_preference: Int
                              ) extends BaseModel

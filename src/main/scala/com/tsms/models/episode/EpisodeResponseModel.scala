package com.tsms.models.episode

import com.tsms.models.BaseModel

/**
 * This model is the response for get Episode/Episodes request
 *
 * @param episode_id episode Id which needs to be fetched
 * @param episode_name episode name
 * @param duration length of the episode in second
 * @param genre genre of the episode
 * @param rating rating of the episode
 * @param casting casting in the episode
 * @param agePreference age preference for which episode is preferred
 */
case class EpisodeResponseModel(
                                 episode_id: String,
                                 episode_name: String,
                                 duration: Int,
                                 genre: String,
                                 rating: Float,
                                 casting: String,
                                 agePreference: Int
                               ) extends BaseModel

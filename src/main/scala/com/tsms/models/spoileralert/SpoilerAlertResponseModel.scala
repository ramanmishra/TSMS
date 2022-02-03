package com.tsms.models.spoileralert

import com.tsms.models.BaseModel

/**
 * This data model is used to give response to a get spoiler alert request
 *
 * @param user_id      user id of the user
 * @param watched_till how far the user have already watched the episode in seconds
 * @param status       status of the wathing
 */
case class SpoilerAlertResponseModel(
                                      user_id: Int,
                                      watched_till: Int,
                                      status: String
                                    ) extends BaseModel
package com.tsms.models.episode

import com.tsms.models.BaseModel

/**
 * This data model is being used as a response to get watch status request
 *
 * @param watched_till how far the user is in watching the episode
 * @param status       status of the watch
 */
case class WatchStatusResponseModel(
                                     watched_till: Int,
                                     status: String
                                   ) extends BaseModel

package com.tsms.models.rating

import com.tsms.models.BaseModel

/**
 * This model is the response of recommendation request
 */
case class RecommendationResponseModel(
                                        seriesId: Int,
                                        seriesName: String,
                                        rating: Float
                                      ) extends BaseModel

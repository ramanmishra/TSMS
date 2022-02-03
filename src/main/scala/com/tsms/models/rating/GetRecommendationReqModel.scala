package com.tsms.models.rating

import com.tsms.constants.TableConstants.{SERIES_TABLE_NAME, USER_TABLE_NAME}
import com.tsms.models.{BaseModel, DatabaseQueryModel}
import com.tsms.transformer.{DBModelTransformer, RecommendationResponseModelTransformer}


/**
 * This data Model is used to get the recommendation for the given user
 *
 * @param userId user Id
 */
case class GetRecommendationReqModel(
                                      userId: Int
                                    ) extends DatabaseQueryModel with BaseModel {
  override def tableName: String = SERIES_TABLE_NAME

  override def query: String = s"Select interest from $USER_TABLE_NAME WHERE user_id = $userId"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(RecommendationResponseModelTransformer)

  override def selectQuery: Option[String] = Some(s"SELECT series_id, series_name, rating FROM $SERIES_TABLE_NAME WHERE genre like order by rating desc limit 5")
}

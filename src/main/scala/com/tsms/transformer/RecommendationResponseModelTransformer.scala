package com.tsms.transformer

import com.tsms.constants.TableConstants.{RECOMMENDATION_SERIES_ID, RECOMMENDATION_SERIES_NAME, RECOMMENDATION_SERIES_RATING}
import com.tsms.models.rating.RecommendationResponseModel

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

/**
 *
 * This is the abstract Transformer data base data into [[com.tsms.models.rating.RecommendationResponseModel]]
 */
object RecommendationResponseModelTransformer extends DBModelTransformer {
  override def buildResult(row: ResultSet): List[RecommendationResponseModel] = {
    val result = ListBuffer.empty[RecommendationResponseModel]
    while (row.next()) {
      result += RecommendationResponseModel(
        row.getInt(RECOMMENDATION_SERIES_ID),
        row.getString(RECOMMENDATION_SERIES_NAME),
        row.getFloat(RECOMMENDATION_SERIES_RATING)
      )
    }

    result.toList
  }
}

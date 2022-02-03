package com.tsms.transformer

import com.tsms.constants.TableConstants._
import com.tsms.models.series.SeriesResponseModel

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

/**
 * This is the abstract Transformer series table into [[com.tsms.models.series.SeriesResponseModel]]
 */
object SeriesResponseModelTransformer extends DBModelTransformer {
  override def buildResult(row: ResultSet): List[SeriesResponseModel] = {
    val result = ListBuffer.empty[SeriesResponseModel]
    while (row.next) {
      result += SeriesResponseModel(
        row.getInt(SERIES_ID),
        row.getString(SERIES_NAME),
        row.getString(GENRE),
        row.getFloat(RATING),
        row.getString(CASTING),
        row.getInt(AGE_PREFERENCE)
      )
    }

    result.toList
  }
}

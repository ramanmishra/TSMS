package com.tsms.transformer

import com.tsms.constants.TableConstants._
import com.tsms.models.BaseModel
import com.tsms.models.season.SeasonResponseModel

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

/**
 * This is the abstract Transformer season table into [[com.tsms.models.season.SeasonResponseModel]]
 */
object SeasonResponseModelTransformer extends DBModelTransformer {
  override def buildResult(row: ResultSet): List[SeasonResponseModel] = {
    val result = ListBuffer.empty[SeasonResponseModel]

    while (row.next()) {
      result += SeasonResponseModel(
        row.getString(SEASON_ID),
        row.getString(SEASON_NAME),
        row.getString(SEASON_GENRE),
        row.getFloat(SEASON_RATING),
        row.getString(SEASON_CASTING),
        row.getInt(SEASON_AGE_PREFERENCE)
      )
    }

    result.toList
  }
}

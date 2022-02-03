package com.tsms.transformer

import com.tsms.constants.TableConstants.{SPOILER_ALERT_STATUS, SPOILER_ALERT_USER_ID, SPOILER_ALERT_WATCHED_TILL}
import com.tsms.models.spoileralert.SpoilerAlertResponseModel

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

/**
 * This is the abstract Transformer watch status table into [[com.tsms.models.spoileralert.SpoilerAlertResponseModel]]
 */
object SpoilerAlertResponseModelTransformer extends DBModelTransformer {
  override def buildResult(row: ResultSet): List[SpoilerAlertResponseModel] = {
    val result = ListBuffer.empty[SpoilerAlertResponseModel]
    while (row.next()) {
      result += SpoilerAlertResponseModel(
        row.getInt(SPOILER_ALERT_USER_ID),
        row.getInt(SPOILER_ALERT_WATCHED_TILL),
        row.getString(SPOILER_ALERT_STATUS)
      )
    }

    result.toList
  }
}

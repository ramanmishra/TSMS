package com.tsms.transformer

import com.tsms.constants.TableConstants.{WATCH_STATUS_STATUS, WATCH_STATUS_WATCHED_TILL}
import com.tsms.models.episode.WatchStatusResponseModel

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

/**
 * This is the abstract Transformer watch status table into [[com.tsms.models.episode.WatchStatusResponseModel]]
 */
object WatchStatusResponseModelTransformer extends DBModelTransformer {
  override def buildResult(row: ResultSet): List[WatchStatusResponseModel] = {
    val result = ListBuffer.empty[WatchStatusResponseModel]
    while (row.next()) {
      result += WatchStatusResponseModel(
        row.getInt(WATCH_STATUS_WATCHED_TILL),
        row.getString(WATCH_STATUS_STATUS)
      )
    }

    result.toList
  }
}

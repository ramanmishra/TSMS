package com.tsms.transformer

import com.tsms.constants.TableConstants._
import com.tsms.models.episode.EpisodeResponseModel

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

/**
 * This is the abstract Transformer episode table into [[com.tsms.models.episode.EpisodeResponseModel]]
 */
object EpisodeResponseModelTransformer extends DBModelTransformer {
  override def buildResult(row: ResultSet): List[EpisodeResponseModel] = {
    val result = ListBuffer.empty[EpisodeResponseModel]

    while (row.next()) {
      result += EpisodeResponseModel(
        row.getString(EPISODE_ID),
        row.getString(EPISODE_NAME),
        row.getInt(EPISODE_DURATION),
        row.getString(EPISODE_GENRE),
        row.getFloat(EPISODE_RATING),
        row.getString(EPISODE_CASTING),
        row.getInt(EPISODE_AGE_PREFERENCE)
      )
    }

    result.toList
  }
}

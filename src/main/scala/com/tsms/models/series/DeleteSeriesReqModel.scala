package com.tsms.models.series

import com.tsms.constants.TableConstants.SERIES_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * this model is being used to get the request to deleted a series for given seriesId
 *
 * @param seriesId series Id of the series which needs to be deleted
 */
case class DeleteSeriesReqModel(seriesId: Int) extends DatabaseQueryModel with BaseSeriesRequest {
  override def tableName: String = SERIES_TABLE_NAME

  override def query: String = s"DELETE FROM $tableName where series_id = $seriesId"

  override def selectQuery: Option[String] = Some(s"SELECT series_id from $tableName where series_id = $seriesId")
}

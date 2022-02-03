package com.tsms.models.series

import com.tsms.constants.TableConstants.SERIES_TABLE_NAME
import com.tsms.models.DatabaseQueryModel
import com.tsms.transformer.{DBModelTransformer, SeriesResponseModelTransformer}

/**
 * This model is being used to fetch all the series exist in system
 *
 * @param seriesId series id is used to do the pagination
 */
case class GetAllSeriesReqModel(seriesId: Int) extends DatabaseQueryModel with BaseSeriesRequest {
  override def tableName: String = SERIES_TABLE_NAME

  override def query: String = s"SELECT * FROM $tableName where series_id >= $seriesId ORDER BY series_id ASC LIMIT 100"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(SeriesResponseModelTransformer)
}

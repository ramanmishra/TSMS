package com.tsms.models.series

import com.tsms.constants.TableConstants.SERIES_TABLE_NAME
import com.tsms.models.DatabaseQueryModel
import com.tsms.transformer.{DBModelTransformer, SeriesResponseModelTransformer}

/**
 * This data model is being used to fetch a single series for given seriesId
 *
 * @param seriesId series Id which supposed to be fetched
 */
case class GetSeriesReqModel(seriesId: Int) extends DatabaseQueryModel with BaseSeriesRequest {
  override def tableName: String = SERIES_TABLE_NAME

  override def query: String = s"SELECT * from $tableName where series_id = $seriesId"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(SeriesResponseModelTransformer)
}

package com.tsms.models.season

import com.tsms.constants.TableConstants.SEASON_TABLE_NAME
import com.tsms.models.DatabaseQueryModel
import com.tsms.transformer.{DBModelTransformer, SeasonResponseModelTransformer}

/**
 * This request is used to get the season for given series Id
 *
 * @param seriesId series Id for which seasons needs to be fetched
 */
case class GetSeasonsReqModel(seriesId: Int) extends DatabaseQueryModel with BaseSeasonRequest {
  override def tableName: String = SEASON_TABLE_NAME

  override def query: String = s"SELECT season_id, season_name, genre, rating, casting, age_preference from $tableName where series_id = $seriesId"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(SeasonResponseModelTransformer)
}

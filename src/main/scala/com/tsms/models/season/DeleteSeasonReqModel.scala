package com.tsms.models.season

import com.tsms.constants.TableConstants.SEASON_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data model is being used to take request to delete a season from a series
 *
 * @param seriesId   series from which the season suppose to be deleted
 * @param seasonName name of the season
 */
case class DeleteSeasonReqModel(seriesId: Int,
                                seasonName: String) extends DatabaseQueryModel with BaseSeasonRequest {
  def createSeasonId: String = s"$seriesId-$seasonName"

  override def tableName: String = SEASON_TABLE_NAME

  override def query: String = s"DELETE FROM $tableName where season_id = '$createSeasonId'"

  override def selectQuery: Option[String] = Some(s"SELECT season_id from $tableName where season_id = '$createSeasonId'")
}

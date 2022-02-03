package com.tsms.models.episode

import com.tsms.constants.TableConstants.EPISODE_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data Model is being use to take request to delete an episode
 *
 * @param seasonId    season Id in which episode belongs
 * @param episodeName episode name
 */
case class DeleteEpisodeReqModel(seasonId: String,
                                 episodeName: String) extends DatabaseQueryModel with BaseEpisodeRequest {
  def createEpisodeId = s"$seasonId-$episodeName"

  override def tableName: String = EPISODE_TABLE_NAME

  override def query: String = s"DELETE FROM $tableName where episode_id = '$createEpisodeId'"

  override def selectQuery: Option[String] = Some(s"SELECT episode_id from $tableName where season_id = '$seasonId'")
}

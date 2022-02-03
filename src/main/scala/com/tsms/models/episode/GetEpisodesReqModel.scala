package com.tsms.models.episode

import com.tsms.constants.TableConstants.EPISODE_TABLE_NAME
import com.tsms.models.DatabaseQueryModel
import com.tsms.transformer.{DBModelTransformer, EpisodeResponseModelTransformer}

/**
 * This data model is used to take request to get the episode for given season Id
 *
 * @param seasonId season Id in which episode belongs
 */
case class GetEpisodesReqModel(seasonId: String) extends DatabaseQueryModel with BaseEpisodeRequest {
  override def tableName: String = EPISODE_TABLE_NAME

  override def query: String = s"SELECT episode_id, episode_name, duration, genre, rating, casting, age_preference FROM  $tableName where season_id = '$seasonId'"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(EpisodeResponseModelTransformer)
}

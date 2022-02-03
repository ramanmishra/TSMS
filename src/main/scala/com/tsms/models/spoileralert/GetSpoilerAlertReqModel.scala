package com.tsms.models.spoileralert

import com.tsms.constants.TableConstants.WATCH_STATUS_TABLE_NAME
import com.tsms.models.DatabaseQueryModel
import com.tsms.transformer.{DBModelTransformer, SpoilerAlertResponseModelTransformer}

/**
 * This data model is used to get spoiler alert for user 1 and user 2
 *
 * @param userId1   user id of 1st user
 * @param userId2   user id of the 2nd user
 * @param episodeId episode id for which we want the spoiler alert
 */
case class GetSpoilerAlertReqModel(userId1: Int,
                                   userId2: Int,
                                   episodeId: String) extends DatabaseQueryModel with BaseSpoilerAlertRequest {
  override def tableName: String = WATCH_STATUS_TABLE_NAME

  override def query: String = s"select user_id, watched_till, status FROM $tableName where user_id in($userId1, $userId2) AND episode_id = '$episodeId'"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(SpoilerAlertResponseModelTransformer)
}

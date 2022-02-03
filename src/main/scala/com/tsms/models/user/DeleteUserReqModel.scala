package com.tsms.models.user

import com.tsms.constants.TableConstants.USER_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This Data model is being used to get the delete request for a specific user
 *
 * @param userId unique user id of an user
 */
case class DeleteUserReqModel(userId: Int) extends DatabaseQueryModel with BaseUserRequest {
  def tableName: String = USER_TABLE_NAME

  def query: String = s"Delete From $tableName where user_id = $userId"

  override def selectQuery: Option[String] = Some(s"SELECT user_id from $tableName where user_id = $userId")
}

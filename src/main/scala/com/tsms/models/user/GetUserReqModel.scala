package com.tsms.models.user

import com.tsms.constants.TableConstants.USER_TABLE_NAME
import com.tsms.models.DatabaseQueryModel
import com.tsms.transformer.{DBModelTransformer, UserResponseModelTransformer}

/**
 * This data model is being used to get detail of a specific user
 *
 * @param userId user id used to query a single user
 */
case class GetUserReqModel(userId: Int) extends DatabaseQueryModel with BaseUserRequest {
  def tableName: String = USER_TABLE_NAME

  val query = s"SELECT * FROM $tableName where user_id = $userId"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(UserResponseModelTransformer)
}

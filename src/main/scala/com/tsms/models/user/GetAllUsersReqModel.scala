package com.tsms.models.user

import com.tsms.constants.TableConstants.USER_TABLE_NAME
import com.tsms.models.DatabaseQueryModel
import com.tsms.transformer.{DBModelTransformer, UserResponseModelTransformer}

/**
 * This data model is being used to request all used exist in the system
 *
 * @param userId is used to do the pagination in case we have lots of users
 */
case class GetAllUsersReqModel(userId: Int) extends DatabaseQueryModel with BaseUserRequest {
  def tableName: String = USER_TABLE_NAME

  def query: String = s"SELECT * FROM $tableName WHERE USER_ID >= $userId ORDER BY USER_ID ASC LIMIT 100"

  override def dbModelTransformer: Option[DBModelTransformer] = Some(UserResponseModelTransformer)
}

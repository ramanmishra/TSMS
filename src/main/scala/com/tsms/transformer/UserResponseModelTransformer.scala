package com.tsms.transformer

import com.tsms.constants.TableConstants._
import com.tsms.models.user.UserResponseModel

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

/**
 * This is the abstract Transformer episode table into [[com.tsms.models.user.UserResponseModel]]
 */
object UserResponseModelTransformer extends DBModelTransformer {
  override def buildResult(row: ResultSet): List[UserResponseModel] = {
    val result = ListBuffer.empty[UserResponseModel]

    while (row.next()) {
      result += UserResponseModel(
        row.getInt(USER_ID),
        row.getString(ADDRESS),
        row.getInt(AGE),
        row.getString(EMAIL_ID),
        row.getString(FIRST_NAME),
        row.getString(INTEREST),
        row.getString(LAST_NAME),
        row.getString(PHONE_NUMBER),
        row.getInt(POSTAL_CODE)
      )
    }

    result.toList
  }
}

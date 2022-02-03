package com.tsms.models.user

import com.tsms.constants.TableConstants.USER_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * this data model is being used to update an existing user
 *
 * @param userId      user id or an existing user
 * @param address     updated address of an existing user
 * @param age         updated age of an existing user
 * @param emailId     updated emailId of an existing user
 * @param firstName   updated first name of an existing user
 * @param interest    updated movie genre interest of an existing user
 * @param lastName    updated last name of an existing user
 * @param phoneNumber updated phone number of an existing user
 * @param postalCode  updated postal codee of an existing user
 */
case class UpdateUserReqModel(
                          userId: Int,
                          address: String,
                          age: Int,
                          emailId: String,
                          firstName: String,
                          interest: String,
                          lastName: String,
                          phoneNumber: String,
                          postalCode: Int
                        ) extends DatabaseQueryModel with BaseUserRequest {

  def tableName: String = USER_TABLE_NAME

  def query: String = s"UPDATE $tableName SET address = '$address', age = $age, email_id = '$emailId', first_name = '$firstName', interest = '$interest', last_name = '$lastName', phone_number = '$phoneNumber', postal_code = $postalCode WHERE user_id = $userId ;"

  override def selectQuery: Option[String] = Some(s"SELECT user_id from $tableName where user_id = $userId")
}

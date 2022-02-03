package com.tsms.models.user

import com.tsms.constants.TableConstants.USER_TABLE_NAME
import com.tsms.models.DatabaseQueryModel

/**
 * This data Model is used to create a new user
 *
 * @param address     address of the an user
 * @param age         age of the an user
 * @param emailId     email id of an user
 * @param firstName   first name of an user
 * @param interest    movie genre of interest of an user
 * @param lastName    last name of an user
 * @param phoneNumber phone number of an user
 * @param postalCode  postal code of the user used to see if two people can go out together
 */
case class CreateUserReqModel(
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

  def query: String =
    s"""INSERT INTO $tableName(postal_code, phone_number, last_name, interest, first_name, email_id, address, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"""

}
package com.tsms.actor

import com.tsms.constants.MiscConstants._
import com.tsms.models.user.CreateUserReqModel

import java.sql.PreparedStatement

/**
 * This is the Utility trait used to set the prepared statement for create user request
 */
trait UserActorUtil {
  def setValues(createUserReqModel: CreateUserReqModel, pstmt: PreparedStatement): Unit = {
    pstmt.setInt(EIGHT, createUserReqModel.age)
    pstmt.setInt(ONE, createUserReqModel.postalCode)
    pstmt.setString(SEVEN, createUserReqModel.address)
    pstmt.setString(SIX, createUserReqModel.emailId)
    pstmt.setString(FIVE, createUserReqModel.firstName)
    pstmt.setString(FOUR, createUserReqModel.interest)
    pstmt.setString(THREE, createUserReqModel.lastName)
    pstmt.setString(TWO, createUserReqModel.phoneNumber)
  }
}

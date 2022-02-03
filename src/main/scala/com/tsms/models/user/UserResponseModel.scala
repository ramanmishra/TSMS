package com.tsms.models.user

import com.tsms.models.BaseModel

case class UserResponseModel(
                      userId: Int,
                      address: String,
                      age: Int,
                      emailId: String,
                      firstName: String,
                      interest: String,
                      lastName: String,
                      phoneNumber: String,
                      postalCode: Int
                    ) extends BaseModel

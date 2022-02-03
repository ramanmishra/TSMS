package com.tsms.models

/**
 *
 * !!!!!!!!!!!!!!!!!!!!ALERT!!!!!!!!!!!!!!!!!!!!
 * This Model is not used yet it can be used to create database config from application.conf file
 * this Model is being used to create the database configuration
 * !!!!!!!!!!!!!!!!!!!!ALERT!!!!!!!!!!!!!!!!!!!!
 *
 * @param user     database user
 * @param password database password
 * @param host     host name/ip of the database default to localhost
 * @param port     port number of the database where it's running
 * @param database name of the database
 * @param schema   schema name in the database
 */
case class DBConfigModel(
                          user: String,
                          password: String,
                          host: String,
                          port: Int,
                          database: String,
                          schema: String
                        ) extends BaseModel {
  def getConnectionUrl: String = {
    s"jdbc:postgresql://$host/$database?user=$user&password=$password"
  }
}

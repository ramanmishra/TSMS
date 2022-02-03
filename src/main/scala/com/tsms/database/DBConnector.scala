package com.tsms.database

import com.typesafe.config.Config
import org.h2.jdbcx.JdbcDataSource

import java.sql.Connection

/**
 * This class Provides all the database related configuration and connection
 *
 * @param config Configuration object to read from application.conf
 */
class DBConnector()(implicit config: Config) {
  val dataSource = new JdbcDataSource()

  dataSource.setURL("jdbc:h2:./tsms.db;INIT=runscript from 'src/main/db_scripts/create_db.sql'")
  dataSource.setUser("tsms_user")
  dataSource.setPassword("tsms_password")
  val dbConn: Connection = dataSource.getConnection()

  def getConnection: Connection = dbConn

  def runMigration(): Unit = {

  }
}

/**
 * Companion Object for class [[com.tsms.database.DBConnector]]
 */
object DBConnector {
  def apply()(implicit config: Config): DBConnector = new DBConnector()
}


package com.tsms.models

import com.tsms.transformer.DBModelTransformer

/**
 * Trait provides the basic data base query functionality
 */
trait DatabaseQueryModel {
  def tableName: String

  def dbModelTransformer: Option[DBModelTransformer] = None

  def query: String

  def selectQuery: Option[String] = None

  def updateQuery: Option[String] = None

  def otherTableUpdateQuery: Option[String] = None
}
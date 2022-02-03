package com.tsms.transformer

import com.tsms.models.BaseModel

import java.sql.ResultSet

/**
 * This is the abstract Transformer to provide basic functionality to convert database table into data models
 */
trait DBModelTransformer {
  def buildResult(row: ResultSet): List[BaseModel]
}
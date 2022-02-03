package com.tsms.utils

import com.typesafe.config.Config

/**
 * Abstract trait which provides application config of `type` [[Config]]
 */
trait AppConfig {
  implicit val config: Config
}

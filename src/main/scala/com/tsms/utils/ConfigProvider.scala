package com.tsms.utils
import com.typesafe.config.{Config, ConfigFactory}

/**
 * This tait provide config to the application
 */
trait ConfigProvider extends AppConfig {
  override implicit val config: Config = ConfigFactory.load()
}

package com.tsms.constants

/**
 * Table constants
 */
object TableConstants {
  //USER TABLE AND COLUMNS
  final val USER_TABLE_NAME = "tsms_schema.user_table"
  final val USER_ID = 1
  final val POSTAL_CODE = 2
  final val PHONE_NUMBER = 3
  final val LAST_NAME = 4
  final val INTEREST = 5
  final val FIRST_NAME = 6
  final val EMAIL_ID = 7
  final val ADDRESS = 8
  final val AGE = 9

  final val SERIES_TABLE_NAME = "tsms_schema.series"
  final val SERIES_ID = 1
  final val SERIES_NAME = 2
  final val GENRE = 3
  final val RATING = 4
  final val CASTING = 5
  final val AGE_PREFERENCE = 6

  final val SEASON_TABLE_NAME = "tsms_schema.season"
  final val SEASON_ID = 1
  final val SEASON_NAME = 2
  final val SEASON_GENRE = 3
  final val SEASON_RATING = 4
  final val SEASON_CASTING = 5
  final val SEASON_AGE_PREFERENCE = 6

  final val EPISODE_TABLE_NAME = "tsms_schema.episode"
  final val EPISODE_ID = 1
  final val EPISODE_NAME = 2
  final val EPISODE_DURATION = 3
  final val EPISODE_GENRE = 4
  final val EPISODE_RATING = 5
  final val EPISODE_CASTING = 6
  final val EPISODE_AGE_PREFERENCE = 7

  final val WATCH_STATUS_TABLE_NAME = "tsms_schema.watch_status"
  final val WATCH_STATUS_WATCHED_TILL = "watched_till"
  final val WATCH_STATUS_STATUS = "status"

  final val SPOILER_ALERT_USER_ID = 1
  final val SPOILER_ALERT_WATCHED_TILL = 2
  final val SPOILER_ALERT_STATUS = 3

  final val SERIES_RATING_TABLE_NAME = "tsms_schema.series_rating"
  final val SEASON_RATING_TABLE_NAME = "tsms_schema.season_rating"
  final val EPISODE_RATING_TABLE_NAME = "tsms_schema.episode_rating"

  final val RECOMMENDATION_SERIES_ID = 1
  final val RECOMMENDATION_SERIES_NAME = 2
  final val RECOMMENDATION_SERIES_RATING = 3
}

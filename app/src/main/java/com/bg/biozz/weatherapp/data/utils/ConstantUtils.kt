package com.bg.biozz.weatherapp.data.utils

class ConstantUtils {
    companion object {
        //Main
        const val ICON_SIZE = 60
        const val ITEM_WEIGHT = 0.2f
        const val HOUR_OF_DAY = 12
        const val METRIC = "metric"

        //Local DB
        val DEFAULT_CITIES_LIST = listOf("Naberezhnyye Chelny", "Yelabuga", "Kazan")
        const val DATABASE_VERSION = 4
        const val NA = "N/A"
        const val DATABASE_NAME = "cityDB"
        const val TABLE_CITYS = "citys"
        const val TABLE_DEFAULT_CITY = "defaultcity"
        const val TABLE_FORECAST = "forecast"
        const val KEY_ID = "_id"
        const val KEY_NAME = "name"
        const val KEY_WEATHER = "weather"
        const val KEY_TEMP_MIN_MAX = "tempMinMax"
        const val KEY_ICON = "icon"
        const val KEY_TEMP = "temp"
        const val KEY_PRESSURE = "pressure"
        const val KEY_HUMIDITY = "humidity"
        const val KEY_DESCRIPTION = "description"
        const val KEY_WIND_SPEED = "windSpeed"
        const val KEY_DAYS_OF_WEEK = "daysOfWeek"
        const val KEY_LAST_UPDATE = "lastUpdate"
    }
}
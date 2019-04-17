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
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "cityDB"
        const val TABLE_CITYS = "citys"
        const val TABLE_DEFAULT_CITY = "defaultcity"
        const val KEY_ID = "_id"
        const val KEY_NAME = "name"
    }
}
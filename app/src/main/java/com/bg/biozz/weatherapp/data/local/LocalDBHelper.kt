package com.bg.biozz.weatherapp.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.bg.biozz.weatherapp.data.utils.ConstantUtils

class LocalDBHelper(context: Context) : SQLiteOpenHelper(context, ConstantUtils.DATABASE_NAME, null, ConstantUtils.DATABASE_VERSION) {
    private val TAG = "LocalDBHelper"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "Create new DB")
        db?.execSQL("CREATE TABLE ${ConstantUtils.TABLE_DEFAULT_CITY} (${ConstantUtils.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${ConstantUtils.KEY_NAME} TEXT)")
        Log.d(TAG, "New default city table created!")

        db?.execSQL("CREATE TABLE ${ConstantUtils.TABLE_CITYS} (" +
                "${ConstantUtils.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${ConstantUtils.KEY_NAME} TEXT, " +
                "${ConstantUtils.KEY_WEATHER} TEXT, " +
                "${ConstantUtils.KEY_TEMP_MIN_MAX} TEXT, " +
                "${ConstantUtils.KEY_ICON} TEXT, " +
                "${ConstantUtils.KEY_TEMP} TEXT, " +
                "${ConstantUtils.KEY_PRESSURE} TEXT, " +
                "${ConstantUtils.KEY_HUMIDITY} TEXT, " +
                "${ConstantUtils.KEY_DESCRIPTION} TEXT, " +
                "${ConstantUtils.KEY_WIND_SPEED} TEXT" +
                "${ConstantUtils.KEY_DT} TEXT" +
                ")")

        db?.execSQL("CREATE TABLE ${ConstantUtils.TABLE_FORECAST} (" +
                "${ConstantUtils.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${ConstantUtils.KEY_DAYS_OF_WEEK} TEXT, " +
                "${ConstantUtils.KEY_ICON} TEXT, " +
                "${ConstantUtils.KEY_TEMP} TEXT, " +
                ")")

        addDefaultCities(db)
        Log.d(TAG, "New DB created!")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "Update DB: old version = $oldVersion | new version = $newVersion")
        db?.execSQL("DROP TABLE IF EXISTS " + ConstantUtils.TABLE_CITYS)
        db?.execSQL("DROP TABLE IF EXISTS " + ConstantUtils.TABLE_DEFAULT_CITY)
        db?.execSQL("DROP TABLE IF EXISTS " + ConstantUtils.TABLE_FORECAST)
        onCreate(db)
    }

    private fun addDefaultCities(db: SQLiteDatabase?){
        for(city in ConstantUtils.DEFAULT_CITIES_LIST){
            val contentValues = ContentValues()

            contentValues.put(ConstantUtils.KEY_NAME, city)
            contentValues.put(ConstantUtils.KEY_WEATHER, ConstantUtils.NA)
            contentValues.put(ConstantUtils.KEY_TEMP_MIN_MAX, ConstantUtils.NA)
            contentValues.put(ConstantUtils.KEY_ICON, ConstantUtils.NA)
            contentValues.put(ConstantUtils.KEY_TEMP, ConstantUtils.NA)
            contentValues.put(ConstantUtils.KEY_PRESSURE, ConstantUtils.NA)
            contentValues.put(ConstantUtils.KEY_HUMIDITY, ConstantUtils.NA)
            contentValues.put(ConstantUtils.KEY_DESCRIPTION, ConstantUtils.NA)
            contentValues.put(ConstantUtils.KEY_WIND_SPEED, ConstantUtils.NA)
            contentValues.put(ConstantUtils.KEY_DT, ConstantUtils.NA)

            db?.insert(ConstantUtils.TABLE_CITYS, null, contentValues)
        }

        val defaultCity = ContentValues()
        defaultCity.put(ConstantUtils.KEY_NAME, ConstantUtils.DEFAULT_CITIES_LIST[0])
        db?.insert(ConstantUtils.TABLE_DEFAULT_CITY, null, defaultCity)
    }
}
package com.bg.biozz.weatherapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.bg.biozz.weatherapp.data.utils.ConstantUtils

class LocalDBHelper(context: Context) : SQLiteOpenHelper(context, ConstantUtils.DATABASE_NAME, null, ConstantUtils.DATABASE_VERSION) {
    private val TAG = "LocalDBHelper"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.w(TAG, "Create new DB")
        db?.execSQL("CREATE TABLE ${ConstantUtils.TABLE_DEFAULT_CITY} (${ConstantUtils.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${ConstantUtils.KEY_NAME} TEXT)")
        Log.w(TAG, "New default city table created!")
        db?.execSQL("CREATE TABLE ${ConstantUtils.TABLE_CITYS} (${ConstantUtils.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${ConstantUtils.KEY_NAME} TEXT)")
        addDefaultCities(db)
        Log.w(TAG, "New DB created!")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.w(TAG, "Update DB: old version = $oldVersion | new version = $newVersion")
        db?.execSQL("DROP TABLE IF IT EXIST " + ConstantUtils.TABLE_CITYS)
        db?.execSQL("DROP TABLE IF IT EXIST " + ConstantUtils.TABLE_DEFAULT_CITY)
        onCreate(db)
    }

    private fun addDefaultCities(db: SQLiteDatabase?){
        for (i in 0 until ConstantUtils.DEFAULT_CITIES_LIST.size - 1) {
            val contentValues = ContentValues()
            contentValues.put(ConstantUtils.KEY_NAME, ConstantUtils.DEFAULT_CITIES_LIST[i])
            db?.insert(ConstantUtils.TABLE_CITYS, null, contentValues)
        }

        val defaultCity = ContentValues()
        defaultCity.put(ConstantUtils.KEY_NAME, ConstantUtils.DEFAULT_CITIES_LIST[0])
        db?.insert(ConstantUtils.TABLE_DEFAULT_CITY, null, defaultCity)
    }
}
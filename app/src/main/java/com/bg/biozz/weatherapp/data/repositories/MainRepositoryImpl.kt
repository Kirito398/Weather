package com.bg.biozz.weatherapp.data.repositories

import android.content.ContentValues
import android.util.Log
import com.bg.biozz.weatherapp.BuildConfig
import com.bg.biozz.weatherapp.data.API
import com.bg.biozz.weatherapp.data.LocalDBHelper
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.repositories.MainRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MainRepositoryImpl(private val webClient: API, private val localClient: LocalDBHelper) : MainRepository{
    private val TAG = "MainRepositoryImpl"

    override fun getCityData(cityName: String): Single<CityData> {
        return webClient
                .messages(cityName, BuildConfig.KEY, ConstantUtils.METRIC)
                .subscribeOn(Schedulers.io())
    }

    override fun getForeCast(cityName: String): Single<ForeCast> {
        return webClient
                .forecast(cityName, BuildConfig.KEY, ConstantUtils.METRIC)
                .subscribeOn(Schedulers.io())
    }

    override fun getCitiesList(): List<String> {
        val mDb = localClient.readableDatabase
        val cursor = mDb.query(
                ConstantUtils.TABLE_CITYS,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )

        val citiesList = mutableListOf<String>()
        if(cursor.moveToFirst()){
            do {
                citiesList.add(cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_NAME)))
            }while (cursor.moveToNext())
        }else{
            Log.e(TAG, "0 rows")
        }
        cursor.close()

        return citiesList
    }

    override fun addCityIntoDB(cityName: String) {
        val mDb = localClient.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(ConstantUtils.KEY_NAME, cityName)
        mDb.insert(ConstantUtils.TABLE_CITYS, null, contentValue)
    }

    override fun setDefaultCity(cityName: String) {
        val mDb = localClient.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ConstantUtils.KEY_NAME, cityName)
        mDb.update(ConstantUtils.TABLE_DEFAULT_CITY, contentValues, null, null)
    }

    override fun getDefaultCity(): String {
        val mDb = localClient.readableDatabase
        val cursor = mDb.query(
                ConstantUtils.TABLE_DEFAULT_CITY,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )

        var cityName: String
        if(cursor.moveToFirst()){
            do {
                cityName = cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_NAME))
            }while (cursor.moveToNext())
        }else{
            cityName = ConstantUtils.DEFAULT_CITIES_LIST[0]
            Log.e(TAG, "0 rows")
        }

        return cityName
    }
}
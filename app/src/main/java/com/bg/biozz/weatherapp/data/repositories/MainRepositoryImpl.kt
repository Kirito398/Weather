package com.bg.biozz.weatherapp.data.repositories

import android.content.ContentValues
import android.util.Log
import com.bg.biozz.weatherapp.BuildConfig
import com.bg.biozz.weatherapp.data.rest.API
import com.bg.biozz.weatherapp.data.local.LocalDBHelper
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainRepositoryImpl(private val webClient: API, private val localClient: LocalDBHelper) : MainInterface.Repository {
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

    override fun getCityDataFromLocalDB(cityName: String): CityViewModel {
        val cityViewModel: CityViewModel
        val mDb = localClient.readableDatabase
        val query = "SELECT * FROM ${ConstantUtils.TABLE_CITYS} WHERE ${ConstantUtils.KEY_NAME}=\"$cityName\""
        val cursor = mDb.rawQuery(query, null)

        cursor.moveToFirst()
        cityViewModel = CityViewModel(
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_WEATHER)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_TEMP_MIN_MAX)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_ICON)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_TEMP)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_PRESSURE)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_HUMIDITY)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_WIND_SPEED)),
                cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_DT))
        )
        cursor.close()

        return cityViewModel
    }

    override fun getForeCastFromLocalDB(cityName: String): ForeCastViewModel {
        val mDb = localClient.readableDatabase
        val query = "SELECT * FROM ${ConstantUtils.TABLE_FORECAST} WHERE ${ConstantUtils.KEY_NAME}=\"$cityName\""
        val cursor = mDb.rawQuery(query, null)

        val daysOfWeek = mutableListOf<String>()
        val icon = mutableListOf<String>()
        val temp = mutableListOf<String>()
        if(cursor.moveToFirst()){
            do{
                daysOfWeek.add(cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_DAYS_OF_WEEK)))
                icon.add(cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_ICON)))
                temp.add(cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_TEMP)))
            } while (cursor.moveToNext())
        } else {
            Log.d(TAG, "0 rows")
        }
        cursor.close()

        return ForeCastViewModel(daysOfWeek, icon, temp)
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
        if (cursor.moveToFirst()) {
            do {
                citiesList.add(cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_NAME)))
            } while (cursor.moveToNext())
        } else {
            Log.d(TAG, "0 rows")
        }
        cursor.close()

        return citiesList
    }

    override fun addCityIntoDB(cityData: CityData) {
        val mDb = localClient.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ConstantUtils.KEY_NAME, cityData.name)
        contentValues.put(ConstantUtils.KEY_WEATHER, cityData.weather[0].main)
        contentValues.put(ConstantUtils.KEY_TEMP_MIN_MAX, "${cityData.main.tempMin.toInt()} / ${cityData.main.tempMax.toInt()}")
        contentValues.put(ConstantUtils.KEY_ICON, cityData.weather[0].icon)
        contentValues.put(ConstantUtils.KEY_TEMP, cityData.main.temp.toInt().toString())
        contentValues.put(ConstantUtils.KEY_PRESSURE, cityData.main.pressure.toInt().toString())
        contentValues.put(ConstantUtils.KEY_HUMIDITY, cityData.main.humidity.toString())
        contentValues.put(ConstantUtils.KEY_DESCRIPTION, cityData.weather[0].description)
        contentValues.put(ConstantUtils.KEY_WIND_SPEED, cityData.wind.speed.toInt().toString())
        contentValues.put(ConstantUtils.KEY_DT, cityData.dt.toString())

        mDb.insert(ConstantUtils.TABLE_CITYS, null, contentValues)

        for(i in 1 .. 5){
            val forecastValues = ContentValues()
            forecastValues.put(ConstantUtils.KEY_NAME, cityData.name)
            forecastValues.put(ConstantUtils.KEY_NUMBER_OF_DAY, i.toString())
            forecastValues.put(ConstantUtils.KEY_DAYS_OF_WEEK, ConstantUtils.NA)
            forecastValues.put(ConstantUtils.KEY_ICON, ConstantUtils.NA)
            forecastValues.put(ConstantUtils.KEY_TEMP, ConstantUtils.NA)
            mDb.insert(ConstantUtils.TABLE_FORECAST, null, forecastValues)
        }
    }

    override fun setDefaultCity(cityName: String) {
        val mDb = localClient.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ConstantUtils.KEY_NAME, cityName)
        mDb.update(ConstantUtils.TABLE_DEFAULT_CITY, contentValues, null, null)
    }

    override fun updateCityDataInLocalDB(cityViewModel: CityViewModel) {
        val mDb = localClient.writableDatabase
        val formatDayOfWeek = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val currentDate = Date()
        val lastUpdateDate = formatDayOfWeek.format(currentDate)

        val contentValues = ContentValues()
        contentValues.put(ConstantUtils.KEY_NAME, cityViewModel.cityName)
        contentValues.put(ConstantUtils.KEY_WEATHER, cityViewModel.weather)
        contentValues.put(ConstantUtils.KEY_TEMP_MIN_MAX, cityViewModel.tempMinMax)
        contentValues.put(ConstantUtils.KEY_ICON, cityViewModel.icon)
        contentValues.put(ConstantUtils.KEY_TEMP, cityViewModel.temp)
        contentValues.put(ConstantUtils.KEY_PRESSURE, cityViewModel.pressure)
        contentValues.put(ConstantUtils.KEY_HUMIDITY, cityViewModel.humidity)
        contentValues.put(ConstantUtils.KEY_DESCRIPTION, cityViewModel.description)
        contentValues.put(ConstantUtils.KEY_WIND_SPEED, cityViewModel.windSpeed)
        contentValues.put(ConstantUtils.KEY_DT, lastUpdateDate)

        mDb.update(ConstantUtils.TABLE_CITYS, contentValues, "${ConstantUtils.KEY_NAME}= ?", arrayOf(cityViewModel.cityName))
    }

    override fun updateForeCastInLocalDB(foreCastViewModel: ForeCastViewModel, cityName: String) {
        val mDb = localClient.writableDatabase

        for(i in 1..foreCastViewModel.daysOfWeek.size){
            val contentValues = ContentValues()
            contentValues.put(ConstantUtils.KEY_DAYS_OF_WEEK, foreCastViewModel.daysOfWeek[i-1])
            contentValues.put(ConstantUtils.KEY_ICON, foreCastViewModel.icon[i-1])
            contentValues.put(ConstantUtils.KEY_TEMP, foreCastViewModel.temp[i-1])
            mDb.update(ConstantUtils.TABLE_FORECAST, contentValues, "${ConstantUtils.KEY_NUMBER_OF_DAY}= ? AND ${ConstantUtils.KEY_NAME}= ?", arrayOf(i.toString(), cityName))
        }
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
        if (cursor.moveToFirst()) {
            do {
                cityName = cursor.getString(cursor.getColumnIndex(ConstantUtils.KEY_NAME))
            } while (cursor.moveToNext())
        } else {
            cityName = ConstantUtils.DEFAULT_CITIES_LIST[0]
            Log.e(TAG, "0 rows")
        }
        cursor.close()

        return cityName
    }
}
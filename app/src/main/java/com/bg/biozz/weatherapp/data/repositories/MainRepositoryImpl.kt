package com.bg.biozz.weatherapp.data.repositories

import com.bg.biozz.weatherapp.data.DBLoader
import com.bg.biozz.weatherapp.data.models.ForeCastModel
import com.bg.biozz.weatherapp.data.models.MessageModel
import com.bg.biozz.weatherapp.domain.Loader
import com.bg.biozz.weatherapp.domain.repositories.MainRepository
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.Days
import com.bg.biozz.weatherapp.domain.models.ForeCastData
import java.text.SimpleDateFormat
import java.util.*

class MainRepositoryImpl : MainRepository, Loader.Callback{
    val mLoader: Loader
    lateinit var mCallback: MainRepository.Callback

    init {
        mLoader = DBLoader(this)
    }

    override fun getCityData() {
        mLoader.loadMessageModel()
    }

    override fun onLoadedMessage(data: MessageModel) {
        val cityData = CityData(
                data.name,
                data.weather[0].main,
                data.main.tempMin,
                data.main.tempMax,
                data.weather[0].icon,
                data.main.temp,
                data.main.pressure,
                data.main.humidity,
                data.weather[0].description,
                data.wind.speed
        )

        mCallback.returnCityData(cityData)
    }

    override fun getForeCast() {
        mLoader.loadForeCast()
    }

    override fun onLoadedForecast(data: ForeCastModel) {
        val formatDayOfWeek = SimpleDateFormat("E")
        val mDaysList = mutableListOf<Days>()

        for(day in data.items){
            if(day.getDate().get(Calendar.HOUR_OF_DAY) == 12){
                val mDays = Days(
                        formatDayOfWeek.format(day.getDate().time),
                        day.weather[0].icon,
                        day.main.temp)
                mDaysList.add(mDays)
            }
        }

        mCallback.returnForeCast(ForeCastData(mDaysList))
    }

    override fun initCallback(callback: MainRepository.Callback) {
        mCallback = callback
    }
}
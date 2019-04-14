package com.bg.biozz.weatherapp.domain.repositories

import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCastData

interface MainRepository {
    fun getForeCast()
    fun getCityData()
    fun initCallback(callback: Callback)

    interface Callback{
        fun returnForeCast(foreCast: ForeCastData)
        fun returnCityData(cityData: CityData)
    }
}
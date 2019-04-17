package com.bg.biozz.weatherapp.domain.repositories

import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCast
import io.reactivex.Single

interface MainRepository {
    fun getCityData(cityName: String): Single<CityData>
    fun getForeCast(cityName: String): Single<ForeCast>
    fun getCitiesList(): List<String>
    fun getDefaultCity(): String
    fun addCityIntoDB(cityName: String)
    fun setDefaultCity(cityName: String)
}
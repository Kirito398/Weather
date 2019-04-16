package com.bg.biozz.weatherapp.domain.interactors

import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCast
import io.reactivex.Single

interface MainInteractor {
    fun getCityData(cityName: String): Single<CityData>
    fun getForeCast(cityName: String): Single<ForeCast>
    fun getDefaultCitiesList(): List<String>
}
package com.bg.biozz.weatherapp.domain.interactors

import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCastData

interface MainInteractor {
    fun loadForeCast()
    fun loadCityData()

    interface Callback{
        fun onLoadedForeCast(foreCast: ForeCastData)
        fun onLoadedCityData(cityData: CityData)
        fun onError(msg: String)
    }
}
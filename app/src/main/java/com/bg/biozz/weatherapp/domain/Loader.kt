package com.bg.biozz.weatherapp.domain

import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.models.CityData

interface Loader {
    fun loadForeCast()
    fun loadMessageModel()

    interface Callback{
        fun onLoadedForecast(data: ForeCast)
        fun onLoadedMessage(data: CityData)
    }
}
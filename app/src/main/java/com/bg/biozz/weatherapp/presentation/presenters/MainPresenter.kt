package com.bg.biozz.weatherapp.presentation.presenters

import com.bg.biozz.weatherapp.domain.models.ForeCastData
import com.bg.biozz.weatherapp.presentation.models.CityViewModel

interface MainPresenter {
    fun getCityData()
    fun getForeCast()

    interface Callback{
        fun onLoadedCityData(cityViewModel: CityViewModel)
        fun onLoadedForeCast(foreCastData: ForeCastData)
        fun onError(msg: String)
    }
}
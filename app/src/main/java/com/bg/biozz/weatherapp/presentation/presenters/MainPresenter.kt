package com.bg.biozz.weatherapp.presentation.presenters

import com.bg.biozz.weatherapp.presentation.models.CityViewModel
import com.bg.biozz.weatherapp.presentation.models.ForeCastViewModel

interface MainPresenter {
    fun getCityData(cityName: String)
    fun getForeCast(cityName: String)
    fun getDefaultCitiesList(): List<String>

    interface Callback{
        fun onLoadedCityData(cityViewModel: CityViewModel)
        fun onLoadedForeCast(foreCastViewModel: ForeCastViewModel)
        fun onLoadedError(msg: String)
    }
}
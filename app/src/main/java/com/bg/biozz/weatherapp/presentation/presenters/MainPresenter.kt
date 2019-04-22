package com.bg.biozz.weatherapp.presentation.presenters

import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel


interface MainPresenter {
    fun getCityData(cityName: String)
    fun getForeCast(cityName: String)
    fun getDefaultCity(): String
    fun getDefaultCitiesList(): List<String>

    interface Callback{
        fun onLoadedCityData(cityViewModel: CityViewModel)
        fun onLoadedForeCast(foreCastViewModel: ForeCastViewModel)
        fun onLoadedError(msg: String)
        fun showMainLoadingProgressBar(show: Boolean)
        fun showItemsLoadingProgressBar(show: Boolean)
    }
}
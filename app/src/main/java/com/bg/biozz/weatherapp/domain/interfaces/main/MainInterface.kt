package com.bg.biozz.weatherapp.domain.interfaces.main

import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import io.reactivex.Single

interface MainInterface {
    interface View{
        fun onLoadedCityData(cityViewModel: CityViewModel)
        fun onLoadedForeCast(foreCastViewModel: ForeCastViewModel)
        fun onLoadedError()
        fun showMainLoadingProgressBar(show: Boolean)
        fun showItemsLoadingProgressBar(show: Boolean)
    }

    interface Interactor{
        fun getCityData(cityName: String): Single<CityData>
        fun getForeCast(cityName: String): Single<ForeCast>
        fun getDefaultCity(): String
        fun getDefaultCitiesList(): List<String>
        fun addNewCity(cityName: String)
        fun setDefaultCity(cityName: String)
    }

    interface Repository{
        fun getCityData(cityName: String): Single<CityData>
        fun getForeCast(cityName: String): Single<ForeCast>
        fun getCitiesList(): List<String>
        fun getDefaultCity(): String
        fun addCityIntoDB(cityName: String)
        fun setDefaultCity(cityName: String)
    }
}
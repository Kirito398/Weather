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
        fun showLastUpdateMessage(show: Boolean)
    }

    interface Interactor{
        fun getCityData(cityName: String): Single<CityData>
        fun getForeCast(cityName: String): Single<ForeCast>
        fun getCityDataFromLocalDB(cityName: String): CityViewModel
        fun getForeCastFromLocalDB(cityName: String): ForeCastViewModel
        fun getDefaultCity(): String
        fun getDefaultCitiesList(): List<String>
        fun addNewCity(cityData: CityData)
        fun setDefaultCity(cityName: String)
        fun updateCityDataInLocalDB(cityViewModel: CityViewModel)
        fun updateForeCastInLocalDB(foreCastViewModel: ForeCastViewModel)
    }

    interface Repository{
        fun getCityData(cityName: String): Single<CityData>
        fun getForeCast(cityName: String): Single<ForeCast>
        fun getCityDataFromLocalDB(cityName: String): CityViewModel
        fun getForeCastFromLocalDB(cityName: String): ForeCastViewModel
        fun getCitiesList(): List<String>
        fun getDefaultCity(): String
        fun addCityIntoDB(cityData: CityData)
        fun setDefaultCity(cityName: String)
        fun updateCityDataInLocalDB(cityViewModel: CityViewModel)
        fun updateForeCastInLocalDB(foreCastViewModel: ForeCastViewModel)
    }
}
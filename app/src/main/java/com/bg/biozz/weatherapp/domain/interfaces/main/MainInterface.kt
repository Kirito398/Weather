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
        fun getCityDataFromLocalDB(cityName: String): Single<CityViewModel>
        fun getForeCastFromLocalDB(cityName: String): Single<ForeCastViewModel>
        fun getDefaultCity(): Single<CityViewModel>
        fun getDefaultCitiesList(): Single<List<CityViewModel>>
        fun addNewCity(cityViewModel: CityViewModel): Single<Unit>
        fun setDefaultCity(cityName: String): Single<Unit>
        fun clearDefaultCity(cityName: String): Single<Unit>
        fun updateCityDataInLocalDB(cityViewModel: CityViewModel)
        fun updateForeCastInLocalDB(foreCastViewModel: ForeCastViewModel)
        fun insertNAForeCastInLocalDB(cityName: String)
    }

    interface Repository{
        fun getCityData(cityName: String): Single<CityData>
        fun getForeCast(cityName: String): Single<ForeCast>
        fun getCityDataFromLocalDB(cityName: String): Single<CityViewModel>
        fun getForeCastFromLocalDB(cityName: String): Single<ForeCastViewModel>
        fun getCitiesList(): Single<List<CityViewModel>>
        fun getDefaultCity(): Single<CityViewModel>
        fun addCityIntoDB(cityViewModel: CityViewModel): Single<Unit>
        fun setDefaultCity(cityName: String): Single<Unit>
        fun clearDefaultCity(cityName: String): Single<Unit>
        fun updateCityDataInLocalDB(cityViewModel: CityViewModel)
        fun updateForeCastInLocalDB(foreCastViewModel: ForeCastViewModel)
        fun insertNAForeCastInLocalDB(cityName: String)
    }

    interface BroadCastReceiver{
        fun onInternetConnectionSuccess()
        fun onInternetConnectionError()
    }
}
package com.bg.biozz.weatherapp.presentation.presenters

import com.bg.biozz.weatherapp.domain.models.CityViewModel

interface SelectCityPresenter {
    fun loadCitiesDataList()
    fun setDefaultCity(cityName: String)

    interface Callback{
        fun addCityOnTheList(cityData: CityViewModel)
        fun onError(msg: String)
        fun showProgressBar(show: Boolean)
    }
}
package com.bg.biozz.weatherapp.presentation.presenters

interface AddCityPresenter {
    fun addNewCity(cityName: String)

    interface Callback{
        fun onAdded()
        fun onError(msg: String)
    }
}
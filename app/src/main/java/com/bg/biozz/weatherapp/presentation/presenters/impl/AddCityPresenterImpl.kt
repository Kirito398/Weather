package com.bg.biozz.weatherapp.presentation.presenters.impl

import android.util.Log
import com.bg.biozz.weatherapp.domain.interactors.MainInteractor
import com.bg.biozz.weatherapp.presentation.presenters.AddCityPresenter

class AddCityPresenterImpl(private val mainInteractor: MainInteractor, private val callback: AddCityPresenter.Callback) : AddCityPresenter {
    val TAG = "AddCityPresenterImpl"

    override fun addNewCity(cityName: String) {
        if(cityName == ""){
            Log.d(TAG, "Incorrect city name!")
            callback.onError("Incorrect city name: $cityName")
        } else {
            Log.d(TAG, "New city added!")
            mainInteractor.addNewCity(cityName)
            callback.onAdded()
        }
    }
}
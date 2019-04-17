package com.bg.biozz.weatherapp.presentation.presenters.impl

import android.util.Log
import com.bg.biozz.weatherapp.domain.interactors.MainInteractor
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.presentation.presenters.AddCityPresenter
import io.reactivex.android.schedulers.AndroidSchedulers

class AddCityPresenterImpl(private val mainInteractor: MainInteractor, private val callback: AddCityPresenter.Callback) : AddCityPresenter {
    val TAG = "AddCityPresenterImpl"

    override fun addNewCity(cityName: String) {
        if(cityName == ""){
            Log.d(TAG, "Incorrect city name!")
            callback.onError("Incorrect city name: $cityName")
        } else {
            mainInteractor.getCityData(cityName)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        result -> onFind(result)
                    },{
                        error -> onError(error)
                    })
        }
    }

    private fun onFind(cityData: CityData){
        mainInteractor.addNewCity(cityData.name)
        Log.d(TAG, "New city added!")
        callback.onAdded()
    }

    private fun onError(t: Throwable){
        Log.d(TAG, "City not found! - ${t.localizedMessage}")
        callback.onError("City not found! - ${t.localizedMessage}")
    }
}
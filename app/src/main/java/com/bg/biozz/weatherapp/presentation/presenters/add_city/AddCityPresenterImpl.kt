package com.bg.biozz.weatherapp.presentation.presenters.add_city

import android.util.Log
import com.bg.biozz.weatherapp.domain.interfaces.add_city.AddCityInterface
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import io.reactivex.android.schedulers.AndroidSchedulers

class AddCityPresenterImpl(private val mainInteractor: MainInterface.Interactor, private val callback: AddCityInterface.View) {
    val TAG = "AddCityPresenterImpl"

    fun addNewCity(cityName: String) {
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
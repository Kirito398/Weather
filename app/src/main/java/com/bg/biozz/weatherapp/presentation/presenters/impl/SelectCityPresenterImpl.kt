package com.bg.biozz.weatherapp.presentation.presenters.impl

import android.util.Log
import com.bg.biozz.weatherapp.domain.interactors.MainInteractor
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.presentation.presenters.SelectCityPresenter
import io.reactivex.android.schedulers.AndroidSchedulers

class SelectCityPresenterImpl(private val mainInteractor: MainInteractor, private val callback: SelectCityPresenter.Callback) : SelectCityPresenter {
    private val TAG = "SelectCityPresenterImpl"

    override fun loadCitiesDataList() {
        val citiesList = mainInteractor.getDefaultCitiesList()
        Log.d(TAG, "Load default city list! ${citiesList.size}")

        callback.showProgressBar(true)

        for (city in citiesList){
            mainInteractor.getCityData(city)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        result -> onCityDataLoaded(result)
                    },{
                        error -> onError(error, "City $city not found!")
                    })
        }
    }

    private fun onCityDataLoaded(cityData: CityData){
        val mCityViewModel = CityViewModel(
                cityData.name,
                cityData.weather[0].main,
                "${cityData.main.tempMin.toInt()} / ${cityData.main.tempMax.toInt()}",
                cityData.weather[0].icon,
                cityData.main.temp.toInt().toString(),
                "Pressure: ${cityData.main.pressure.toInt()}",
                "Humidity: ${cityData.main.humidity}",
                cityData.weather[0].description,
                "Wind Speed: ${cityData.wind.speed.toInt()}"
        )

        callback.showProgressBar(false)
        Log.d(TAG, "CityData loaded! - ${cityData.name}")
        callback.addCityOnTheList(mCityViewModel)
    }

    override fun setDefaultCity(cityName: String) {
        mainInteractor.setDefaultCity(cityName)
    }

    private fun onError(t: Throwable, msg: String){
        callback.showProgressBar(false)
        Log.d(TAG, t.localizedMessage + ": " + msg)
        callback.onError(msg)
    }
}
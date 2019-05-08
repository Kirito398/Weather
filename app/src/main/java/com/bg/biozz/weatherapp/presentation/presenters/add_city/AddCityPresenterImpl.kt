package com.bg.biozz.weatherapp.presentation.presenters.add_city

import android.util.Log
import com.bg.biozz.weatherapp.domain.interfaces.add_city.AddCityInterface
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

class AddCityPresenterImpl(private val mainInteractor: MainInterface.Interactor, private val callback: AddCityInterface.View) {
    val TAG = "AddCityPresenterImpl"

    fun addNewCity(cityName: String) {
        if(cityName == ""){
            Log.d(TAG, "Incorrect city name!")
            callback.showIncorrectCityName(cityName)
        } else {
            var d: Disposable? = null
            d = mainInteractor.getCityData(cityName)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        onFind(it)
                        d?.dispose()
                    },{
                        onError(it, "City not found! - ")
                        d?.dispose()
                    })
        }
    }

    private fun onFind(cityData: CityData){
        val formatDayOfWeek = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val currentDate = Date()
        val lastUpdateDate = formatDayOfWeek.format(currentDate)

        val mCityViewModel = CityViewModel(
                cityData.name,
                cityData.weather[0].main,
                "${cityData.main.tempMin.toInt()} / ${cityData.main.tempMax.toInt()}",
                cityData.weather[0].icon,
                cityData.main.temp.toInt().toString(),
                cityData.main.pressure.toInt().toString(),
                cityData.main.humidity.toString(),
                cityData.weather[0].description,
                cityData.wind.speed.toInt().toString(),
                lastUpdateDate
        )

        var disposable: Disposable? = null
        disposable = mainInteractor.addNewCity(mCityViewModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    clearDefaultCity(mCityViewModel.cityName, true)
                    disposable?.dispose()
                },{
                    clearDefaultCity(mCityViewModel.cityName, false)
                    disposable?.dispose()
                })
    }

    private fun clearDefaultCity(cityName: String, isAdded: Boolean){
        if(isAdded)
            mainInteractor.insertNAForeCastInLocalDB(cityName)

        var disposable: Disposable? = null
        disposable = mainInteractor.clearDefaultCity(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    doAfterClearDefaultCity(cityName)
                    disposable?.dispose()
                },{
                    error -> onError(error, "Error in clearing default city!")
                    disposable?.dispose()
                })
    }

    private fun doAfterClearDefaultCity(cityName: String){
        Log.d(TAG, "Default cities cleared!")

        var disposable: Disposable? = null
        disposable = mainInteractor.setDefaultCity(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onAddedSuccess(cityName)
                    disposable?.dispose()
                },{
                    error -> onError(error, "Error setted city $cityName default!")
                    disposable?.dispose()
                })
    }

    private fun onAddedSuccess(cityName: String){
        Log.d(TAG, "New city $cityName added!")
        callback.onAdded()
    }

    private fun onError(t: Throwable, msg: String){
        Log.d(TAG, "$msg ${t.localizedMessage}")
        callback.onError(t.localizedMessage)
    }
}
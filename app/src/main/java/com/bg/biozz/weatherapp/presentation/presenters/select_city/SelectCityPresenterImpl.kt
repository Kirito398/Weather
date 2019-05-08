package com.bg.biozz.weatherapp.presentation.presenters.select_city

import android.util.Log
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.interfaces.select_city.SelectCityInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

class SelectCityPresenterImpl(private val mainInteractor: MainInterface.Interactor, private val selectCityView: SelectCityInterface.View) {
    private val TAG = "SelectCityPresenterImpl"

    fun loadData(isInternetConnectionSuccess: Boolean){
        selectCityView.showProgressBar(true)
        var d: Disposable? = null
        d = mainInteractor.getDefaultCitiesList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onLoadedCitiesDataList(it, isInternetConnectionSuccess)
                    d?.dispose()
                },{
                    onError(it, "Cities list!")
                    d?.dispose()
                })
    }

    private fun onLoadedCitiesDataList(citiesDataList: List<CityViewModel>, isInternetConnectionSuccess: Boolean){
        if(isInternetConnectionSuccess){
            loadCitiesDataListFromInternet(citiesDataList)
        }else{
            loadCitiesDataListFromLocalDB(citiesDataList)
        }
    }

    private fun loadCitiesDataListFromInternet(citiesDataList: List<CityViewModel>) {
        for (city in citiesDataList) {
            var d:Disposable? = null
            d = mainInteractor.getCityData(city.cityName)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        onCityDataLoaded(it)
                        d?.dispose()
                    }, {
                        onError(it, "Load from internet error: ${city.cityName}!")
                        d?.dispose()
                    })
        }

        loadCitiesDataListFromLocalDB(citiesDataList)
    }

    private  fun loadCitiesDataListFromLocalDB(citiesDataList: List<CityViewModel>){
        selectCityView.cleanCityList()
        for (city in citiesDataList) {
            selectCityView.addCityOnTheList(city)
        }
        selectCityView.showProgressBar(false)
    }

    private fun onCityDataLoaded(cityData: CityData){
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

        mainInteractor.updateCityDataInLocalDB(mCityViewModel)
        Log.d(TAG, "CityData loaded! - ${cityData.name}")
        //selectCityView.addCityOnTheList(mCityViewModel)
    }

    fun setDefaultCity(cityName: String) {
        var disposable: Disposable? = null
        disposable = mainInteractor.clearDefaultCity(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    doAfterClearDefaultCity(cityName)
                    disposable?.dispose()
                },{
                    onError(it, "Error in clearing default city!")
                    disposable?.dispose()
                })
        mainInteractor.setDefaultCity(cityName)
    }

    private fun doAfterClearDefaultCity(cityName: String){
        Log.d(TAG, "Default cities cleared!")

        var disposable: Disposable? = null
        disposable = mainInteractor.setDefaultCity(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "City $cityName setted default!")
                    disposable?.dispose()
                },{
                    error -> onError(error, "Error setted city $cityName default!")
                    disposable?.dispose()
                })
    }

    private fun onError(t: Throwable, msg: String){
        Log.d(TAG, t.localizedMessage + ": " + msg)
        selectCityView.showProgressBar(false)
        selectCityView.onError(msg)
    }
}
package com.bg.biozz.weatherapp.presentation.presenters.main

import android.util.Log
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

class MainPresenterImpl(private val mainInteractor: MainInterface.Interactor, private val mainView: MainInterface.View) {
    private val TAG = "MainPresenterImpl"

    fun loadData(isInternetConnectionSuccess: Boolean){
        mainView.showMainLoadingProgressBar(true)

        Log.d(TAG, "Load Data!")
        var d: Disposable? = null
        d = mainInteractor.getDefaultCity()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadDefaultCityData(it, isInternetConnectionSuccess)
                    d?.dispose()
                },{
                    onLoadedError(it, "getDefaultCity")
                    d?.dispose()
                })
    }

    private fun loadDefaultCityData(defaultCity: CityViewModel, isInternetConnectionSuccess: Boolean){
        Log.d(TAG, "City = $defaultCity")
        mainView.onLoadedCityData(defaultCity)
        getForeCastFromLocalDB(defaultCity.cityName, isInternetConnectionSuccess)
        mainView.showLastUpdateMessage(true)
    }

    private fun getForeCastFromLocalDB(cityName: String, isInternetConnectionSuccess: Boolean){
        mainView.showItemsLoadingProgressBar(true)

        var d:Disposable? = null
        d = mainInteractor.getForeCastFromLocalDB(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onLoadedForeCastFromLocalDB(it, isInternetConnectionSuccess)
                    d?.dispose()
                },{
                    onLoadedError(it, "getForeCastFromLocalDB")
                    d?.dispose()
                })
    }

    private fun onLoadedForeCastFromLocalDB(foreCastViewModel: ForeCastViewModel, isInternetConnectionSuccess: Boolean){
        mainView.onLoadedForeCast(foreCastViewModel)

        if(isInternetConnectionSuccess) {
            getCityData(foreCastViewModel.cityName)
            getForeCast(foreCastViewModel.cityName)
        }else{
            mainView.showMainLoadingProgressBar(false)
            mainView.showItemsLoadingProgressBar(false)
        }
    }

    private fun getCityData(cityName: String) {
        var d:Disposable? = null
        d = mainInteractor.getCityData(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onLoadedCityData(it)
                    d?.dispose()
                },{
                    onLoadedError(it, "getCityData")
                    d?.dispose()
                })
    }

    private fun getForeCast(cityName: String) {
        var d:Disposable? = null
        d = mainInteractor.getForeCast(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onLoadedForeCast(it)
                    d?.dispose()
                },{
                    onLoadedError(it, "getForeCast")
                    d?.dispose()
                })
    }

    private fun onLoadedCityData(cityData: CityData) {
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
        mainView.showLastUpdateMessage(false)
        mainView.showMainLoadingProgressBar(false)

        Log.d("MainPresenterImpl", "CityData loaded!")
        mainView.onLoadedCityData(mCityViewModel)
    }

    private fun onLoadedForeCast(foreCast: ForeCast){
        val formatDayOfWeek = SimpleDateFormat("E")
        val daysOfWeek = mutableListOf<String>()
        val icons = mutableListOf<String>()
        val temps = mutableListOf<String>()

        for(day in foreCast.items) {
            if(day.getDate().get(Calendar.HOUR_OF_DAY) == ConstantUtils.HOUR_OF_DAY) {
                daysOfWeek.add(formatDayOfWeek.format(day.getDate().time))
                icons.add(day.weather[0].icon)
                temps.add(day.main.temp.toInt().toString())
            }
        }

        val mForeCast = ForeCastViewModel(
                foreCast.city.name,
                daysOfWeek,
                icons,
                temps
        )

        mainInteractor.updateForeCastInLocalDB(mForeCast)
        mainView.showLastUpdateMessage(false)
        mainView.showItemsLoadingProgressBar(false)

        Log.d(TAG, "ForeCast loaded!")
        mainView.onLoadedForeCast(mForeCast)
    }

    private fun onLoadedError(t: Throwable, msg: String){
        mainView.showMainLoadingProgressBar(false)
        mainView.showItemsLoadingProgressBar(false)

        Log.d(TAG, "$msg Load Error! - ${t.localizedMessage}")
        mainView.onLoadedError()
    }
}
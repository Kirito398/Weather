package com.bg.biozz.weatherapp.presentation.presenters.main

import android.util.Log
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*

class MainPresenterImpl(private val mainInteractor: MainInterface.Interactor, private val mainView: MainInterface.View) {
    fun getCityData(cityName: String) {
        mainView.showMainLoadingProgressBar(true)

        mainInteractor.getCityData(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result->onLoadedCityData(result)
                },{
                    error->onLoadedError(error, "getCityData")
                })
    }

    fun getForeCast(cityName: String) {
        mainView.showItemsLoadingProgressBar(true)

        mainInteractor.getForeCast(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result->onLoadedForeCast(result)
                },{
                    error->onLoadedError(error, "getForeCast")
                })
    }

    private fun onLoadedCityData(cityData: CityData) {
        val mCityViewModel = CityViewModel(
                cityData.name,
                cityData.weather[0].main,
                "${cityData.main.tempMin.toInt()} / ${cityData.main.tempMax.toInt()}",
                cityData.weather[0].icon,
                cityData.main.temp.toInt().toString(),
                cityData.main.pressure.toInt().toString(),
                cityData.main.humidity.toString(),
                cityData.weather[0].description,
                cityData.wind.speed.toInt().toString()
        )

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
                daysOfWeek,
                icons,
                temps
        )

        mainView.showItemsLoadingProgressBar(false)
        Log.d("MainPresenterImpl", "ForeCast loaded!")
        mainView.onLoadedForeCast(mForeCast)
    }

    fun getDefaultCity(): String {
        return mainInteractor.getDefaultCity()
    }

    private fun onLoadedError(t: Throwable, msg: String){
        mainView.showMainLoadingProgressBar(false)
        mainView.showItemsLoadingProgressBar(false)

        Log.d("MainPresenterImpl", "$msg Load Error! - ${t.localizedMessage}")
        mainView.onLoadedError()
    }
}
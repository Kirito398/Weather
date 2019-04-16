package com.bg.biozz.weatherapp.presentation.presenters.impl

import android.util.Log
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.interactors.MainInteractor
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.presentation.models.CityViewModel
import com.bg.biozz.weatherapp.presentation.models.ForeCastViewModel
import com.bg.biozz.weatherapp.presentation.presenters.MainPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*

class MainPresenterImpl(private val mainInteractor: MainInteractor, private val callback: MainPresenter.Callback) : MainPresenter{
    override fun getCityData(cityName: String) {
        mainInteractor.getCityData(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result->onLoadedCityData(result)
                },{
                    error->onLoadedError(error, "getCityData")
                })
    }

    override fun getForeCast(cityName: String) {
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
                "Temperature: ${cityData.main.tempMin.toInt()} / ${cityData.main.tempMax.toInt()}",
                cityData.weather[0].icon,
                cityData.main.temp.toInt().toString(),
                "Pressure: ${cityData.main.pressure.toInt()}",
                "Humidity: ${cityData.main.humidity}",
                cityData.weather[0].description,
                "Wind Speed: ${cityData.wind.speed.toInt()}"
        )

        Log.d("MainPresenterImpl", "CityData loaded!")
        callback.onLoadedCityData(mCityViewModel)
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

        Log.d("MainPresenterImpl", "ForeCast loaded!")
        callback.onLoadedForeCast(mForeCast)
    }

    override fun getDefaultCitiesList(): List<String> {
        return mainInteractor.getDefaultCitiesList()
    }

    override fun addNewCity(cityName: String) {
        mainInteractor.addNewCity(cityName)
    }

    private fun onLoadedError(t: Throwable, msg: String){
        Log.d("MainPresenterImpl", "Load Error!")
        callback.onLoadedError("Load Error: $msg")
    }
}
package com.bg.biozz.weatherapp.presentation.presenters.impl

import android.util.Log
import com.bg.biozz.weatherapp.data.repositories.MainRepositoryImpl
import com.bg.biozz.weatherapp.domain.interactors.MainInteractor
import com.bg.biozz.weatherapp.domain.interactors.impl.MainInteractorImpl
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCastData
import com.bg.biozz.weatherapp.presentation.models.CityViewModel
import com.bg.biozz.weatherapp.presentation.presenters.MainPresenter

class MainPresenterImpl(callback: MainPresenter.Callback) : MainPresenter, MainInteractor.Callback{
    val mCallback = callback
    private var mMainInteractor: MainInteractor = MainInteractorImpl(MainRepositoryImpl(),this)

    override fun getCityData() {
        mMainInteractor.loadCityData()
    }

    override fun getForeCast() {
        mMainInteractor.loadForeCast()
    }

    override fun onLoadedForeCast(foreCast: ForeCastData) {
        Log.d("MainPresenterImpl", "Forecast loaded!")
        mCallback.onLoadedForeCast(foreCast)
    }

    override fun onLoadedCityData(cityData: CityData) {
        Log.d("MainPresenterImpl", "CityData loaded!")
        val mCityViewModel = CityViewModel(
                cityData.cityName,
                cityData.weather,
                cityData.tempMin.toInt().toString(),
                cityData.tempMax.toInt().toString(),
                cityData.icon,
                cityData.temp.toInt().toString(),
                cityData.pressure.toInt().toString(),
                cityData.humidity.toString(),
                cityData.description,
                cityData.windSpeed.toInt().toString()
        )
        mCallback.onLoadedCityData(mCityViewModel)
    }

    override fun onError(msg: String) {
        mCallback.onError(msg)
    }
}
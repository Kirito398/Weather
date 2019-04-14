package com.bg.biozz.weatherapp.domain.interactors.impl

import com.bg.biozz.weatherapp.domain.interactors.MainInteractor
import com.bg.biozz.weatherapp.domain.repositories.MainRepository
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCastData

class MainInteractorImpl(mainRepository: MainRepository, callback: MainInteractor.Callback) : MainInteractor, MainRepository.Callback {
    var mCallback = callback
    val mMainRepository = mainRepository

    init {
        mMainRepository.initCallback(this)
    }

    override fun loadForeCast() {
        mMainRepository.getForeCast()
    }

    override fun returnForeCast(foreCast: ForeCastData) {
        mCallback.onLoadedForeCast(foreCast)
    }

    override fun loadCityData() {
        mMainRepository.getCityData()
    }

    override fun returnCityData(cityData: CityData) {
        mCallback.onLoadedCityData(cityData)
    }

    fun notifyError(msg: String){
        mCallback.onError(msg)
    }
}
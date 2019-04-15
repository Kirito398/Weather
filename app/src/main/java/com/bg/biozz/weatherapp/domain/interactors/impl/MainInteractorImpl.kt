package com.bg.biozz.weatherapp.domain.interactors.impl

import com.bg.biozz.weatherapp.domain.interactors.MainInteractor
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.repositories.MainRepository
import io.reactivex.Single

class MainInteractorImpl(private val mainRepository: MainRepository) : MainInteractor {
    override fun getCityData(cityName: String): Single<CityData> {
        return mainRepository.getCityData(cityName)
    }

    override fun getForeCast(cityName: String): Single<ForeCast> {
        return mainRepository.getForeCast(cityName)
    }
}
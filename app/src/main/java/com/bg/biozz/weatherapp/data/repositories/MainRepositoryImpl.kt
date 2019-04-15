package com.bg.biozz.weatherapp.data.repositories

import com.bg.biozz.weatherapp.BuildConfig
import com.bg.biozz.weatherapp.data.API
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.repositories.MainRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MainRepositoryImpl(private val client: API) : MainRepository{
    override fun getCityData(cityName: String): Single<CityData> {
        return client
                .messages(cityName, BuildConfig.KEY, ConstantUtils.METRIC)
                .subscribeOn(Schedulers.io())
    }

    override fun getForeCast(cityName: String): Single<ForeCast> {
        return client
                .forecast(cityName, BuildConfig.KEY, ConstantUtils.METRIC)
                .subscribeOn(Schedulers.io())
    }
}
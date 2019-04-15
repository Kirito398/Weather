package com.bg.biozz.weatherapp.data

import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.models.CityData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("weather")
    fun messages(@Query("q") cityName: String, @Query("APPID") key: String, @Query("units") metric: String): Single<CityData>

    @GET("forecast")
    fun forecast(@Query("q") cityName: String, @Query("APPID") key: String, @Query("units") metric: String): Single<ForeCast>
}
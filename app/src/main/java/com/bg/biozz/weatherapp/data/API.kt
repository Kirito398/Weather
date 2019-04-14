package com.bg.biozz.weatherapp.data

import com.bg.biozz.weatherapp.data.models.ForeCastModel
import com.bg.biozz.weatherapp.data.models.MessageModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("weather")
    fun messages(@Query("q") cityName: String, @Query("APPID") key: String, @Query("units") metric: String): Observable<MessageModel>

    @GET("forecast")
    fun forecast(@Query("q") cityName: String, @Query("APPID") key: String, @Query("units") metric: String): Observable<ForeCastModel>
}
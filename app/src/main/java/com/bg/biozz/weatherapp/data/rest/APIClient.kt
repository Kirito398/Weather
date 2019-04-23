package com.bg.biozz.weatherapp.data.rest

import com.bg.biozz.weatherapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    fun getClient(): API {
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(API::class.java)
    }
}
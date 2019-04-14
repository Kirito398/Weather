package com.bg.biozz.weatherapp.data

import android.util.Log
import com.bg.biozz.weatherapp.domain.Loader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DBLoader(callback: Loader.Callback) : Loader {
    private val baseURL = "http://api.openweathermap.org/data/2.5/"
    private val KEY = "c63805d4c51f376eeb0b0c242ffef6eb"
    private val METRIC = "metric"
    private var retrofit: Retrofit
    private var messageAPI: API
    private val cityNames: List<String>
    private val mCallback = callback

    init {
        cityNames = listOf("Naberezhnyye Chelny", "Yelabuga", "Kazan")

        retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        messageAPI = retrofit.create(API::class.java)
    }

    override fun loadMessageModel() {
        messageAPI.messages("Yelabuga", KEY, METRIC).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("Load Message", "Yes")
                    mCallback.onLoadedMessage(result)
                },{error ->
                    Log.d("Load Message", "No")
                    error.printStackTrace()
                })
    }

    override fun loadForeCast() {
        messageAPI.forecast("Yelabuga", KEY, METRIC).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("Load ForeCast", "Yes")
                    mCallback.onLoadedForecast(result)
                },{error ->
                    Log.d("Load ForeCast", "No")
                    error.printStackTrace()
                })
    }
}

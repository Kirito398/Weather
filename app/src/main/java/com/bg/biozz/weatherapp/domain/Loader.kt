package com.bg.biozz.weatherapp.domain

import com.bg.biozz.weatherapp.data.models.ForeCastModel
import com.bg.biozz.weatherapp.data.models.MessageModel

interface Loader {
    fun loadForeCast()
    fun loadMessageModel()

    interface Callback{
        fun onLoadedForecast(data: ForeCastModel)
        fun onLoadedMessage(data: MessageModel)
    }
}
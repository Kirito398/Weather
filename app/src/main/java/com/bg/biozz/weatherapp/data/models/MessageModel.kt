package com.bg.biozz.weatherapp.data.models

import java.util.*

data class MessageModel(
        val weather: List<WeatherModel>,
        val main: MainModel,
        val wind: WindModel,
        val dt: Long,
        val name: String
){
    fun getDate(): Calendar{
        val date = Calendar.getInstance()
        date.timeInMillis = dt*1000
        return date
    }
}
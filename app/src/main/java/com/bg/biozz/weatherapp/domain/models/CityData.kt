package com.bg.biozz.weatherapp.domain.models

import java.util.*

data class CityData(
        val name: String,
        val weather: List<Weather>,
        val main: Main,
        val wind: Wind,
        val dt: Long
){
    fun getDate(): Calendar{
        val date = Calendar.getInstance()
        date.timeInMillis = dt*1000
        return date
    }
}
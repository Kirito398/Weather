package com.bg.biozz.weatherapp.domain.models

import java.util.*

data class CityData(
        val weather: List<Weather>,
        val main: Main,
        val wind: Wind,
        val dt: Long,
        val name: String
){
    fun getDate(): Calendar{
        val date = Calendar.getInstance()
        date.timeInMillis = dt*1000
        return date
    }
}
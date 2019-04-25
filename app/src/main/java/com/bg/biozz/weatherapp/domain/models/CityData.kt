package com.bg.biozz.weatherapp.domain.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class CityData(
        @PrimaryKey
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
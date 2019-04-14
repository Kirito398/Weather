package com.bg.biozz.weatherapp.domain.models

data class CityData(
        val cityName: String,
        val weather: String,
        val tempMin: Float,
        val tempMax: Float,
        val icon: String,
        val temp: Float,
        val pressure: Float,
        val humidity: Int,
        val description: String,
        val windSpeed: Float
)
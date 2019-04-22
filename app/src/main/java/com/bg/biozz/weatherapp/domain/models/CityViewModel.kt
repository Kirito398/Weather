package com.bg.biozz.weatherapp.domain.models

data class CityViewModel(
        val cityName: String,
        val weather: String,
        val tempMinMax: String,
        val icon: String,
        val temp: String,
        val pressure: String,
        val humidity: String,
        val description: String,
        val windSpeed: String
)
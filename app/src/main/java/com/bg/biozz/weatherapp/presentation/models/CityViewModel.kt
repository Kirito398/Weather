package com.bg.biozz.weatherapp.presentation.models

data class CityViewModel(
        val cityName: String,
        val weather: String,
        val tempMin: String,
        val tempMax: String,
        val icon: String,
        val temp: String,
        val pressure: String,
        val humidity: String,
        val description: String,
        val windSpeed: String
)
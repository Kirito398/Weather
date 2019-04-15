package com.bg.biozz.weatherapp.domain.models

import com.google.gson.annotations.SerializedName

class Main(
        val temp: Float,
        val pressure: Float,
        val humidity: Int,
        @SerializedName("temp_min")
        val tempMin: Float,
        @SerializedName("temp_max")
        val tempMax: Float
)
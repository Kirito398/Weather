package com.bg.biozz.weatherapp.domain.models

import com.google.gson.annotations.SerializedName

data class ForeCast(
        val city: CityModel,
        @SerializedName("list")
        val items: List<CityData>
)
package com.bg.biozz.weatherapp.domain.models

import com.google.gson.annotations.SerializedName

data class ForeCast(
        @SerializedName("list")
        val items: List<CityData>
)
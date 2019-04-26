package com.bg.biozz.weatherapp.domain.models

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ForeCast(
        val city: CityModel,
        @SerializedName("list")
        val items: List<CityData>
)
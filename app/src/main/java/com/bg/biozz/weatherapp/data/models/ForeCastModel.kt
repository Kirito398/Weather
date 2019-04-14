package com.bg.biozz.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class ForeCastModel(
        @SerializedName("list")
        val items: List<MessageModel>
)
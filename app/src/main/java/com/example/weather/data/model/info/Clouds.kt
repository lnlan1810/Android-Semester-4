package com.example.weather.data.model.info

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)

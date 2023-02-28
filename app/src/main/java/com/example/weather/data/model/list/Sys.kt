package com.example.weather.data.model.list

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String
)
package com.example.weather.data.model.list

import com.google.gson.annotations.SerializedName

data class WeatherList(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<City>,
    @SerializedName("message")
    val message: String
)

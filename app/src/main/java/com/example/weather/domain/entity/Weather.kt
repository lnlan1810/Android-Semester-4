package com.example.weather.domain.entity

data class Weather(
    val id: Int,
    val name: String,
    val countryName: String,
    val latitude: Double,
    val longitude: Double,
    val temp: Double,
    val icon: String,
    val desc: String,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val windDir: String,
    val date: String,
    val sunrise: String,
    val sunset: String,
)

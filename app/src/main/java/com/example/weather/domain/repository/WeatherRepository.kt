package com.example.weather.domain.repository

import com.example.weather.data.api.response.WeatherResponse
import com.example.weather.domain.entity.Cities
import com.example.weather.domain.entity.Weather
import com.example.weather.presentation.recyclerview.models.CityItem


interface WeatherRepository {

    suspend fun getWeatherByName(city: String): WeatherResponse

    suspend fun getWeatherById(id: Int): WeatherResponse

    suspend fun getNearCities(
        latitude: Double,
        longitude: Double,
        count: Int
    ): List<CityItem>
}

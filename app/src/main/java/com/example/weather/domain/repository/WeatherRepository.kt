package com.example.weather.domain.repository

import com.example.weather.domain.entity.Cities
import com.example.weather.domain.entity.Weather


interface WeatherRepository {

    suspend fun getWeatherByName(city: String): Weather

    suspend fun getWeatherById(id: Int): Weather

    suspend fun getNearCities(latitude: Double, longitude: Double, count: Int): Cities
}

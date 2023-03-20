package com.example.weather.data.api

import com.example.weather.data.api.mapper.WeatherMapper
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.entity.Cities
import com.example.weather.data.api.WeatherApi

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val weatherMapper: WeatherMapper,
) : WeatherRepository {
    override suspend fun getWeatherByName(
        city: String
    ): Weather = weatherMapper.toWeather(api.getWeatherByName(city))

    override suspend fun getWeatherById(
        id: Int
    ): Weather = weatherMapper.toWeather(api.getWeatherById(id))

    override suspend fun getNearCities(
        latitude: Double,
        longitude: Double,
        count: Int
    ): Cities = weatherMapper.toListWeather(api.getNearCities(latitude, longitude, count))
}

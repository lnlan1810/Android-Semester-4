package com.example.weather.data.api

import com.example.weather.data.api.mapper.WeatherMapper
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.entity.Cities
import com.example.weather.data.api.WeatherApi
import com.example.weather.data.api.response.WeatherResponse
import com.example.weather.presentation.recyclerview.models.CityItem
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherRepositoryImpl (
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherById(id: Int): WeatherResponse = api.getWeatherById(id)

    override suspend fun getWeatherByName(q: String): WeatherResponse = api.getWeatherByName(q)

    override suspend fun getNearCities(
        latitude: Double,
        longitude: Double,
        count: Int
    ): List<CityItem> = api.getNearCities(latitude, longitude, count).list.map {
        CityItem(
            it.id, it.name, it.main.temp.roundToInt(),
            "https://openweathermap.org/img/w/${it.weather[0].icon}.png"
        )
    }
}

package com.example.weather.data.api

import org.junit.Assert.*
import com.example.weather.data.api.WeatherApi
import com.example.weather.data.api.mapper.WeatherMapper
import com.example.weather.data.api.model.WeatherInfo
import com.example.weather.data.api.model.WeatherList
import com.example.weather.domain.entity.Cities
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {
    private lateinit var weatherApi: WeatherApi
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherApi = mockk()
        weatherMapper = mockk()
        weatherRepository = WeatherRepositoryImpl(weatherApi, weatherMapper)
    }

    @Test
    fun testGetWeatherByName() = runBlocking {
        val cityName = "London"
        val weatherInfo = mockk<WeatherInfo>()
        val weather = mockk<Weather>()

        coEvery { weatherApi.getWeatherByName(cityName) } returns weatherInfo
        coEvery { weatherMapper.toWeather(weatherInfo) } returns weather

        val result = weatherRepository.getWeatherByName(cityName)

        assertEquals(weather, result)
    }

    @Test
    fun testGetWeatherById() = runBlocking {
        val cityId = 123
        val weatherInfo = mockk<WeatherInfo>()
        val weather = mockk<Weather>()

        coEvery { weatherApi.getWeatherById(cityId) } returns weatherInfo
        coEvery { weatherMapper.toWeather(weatherInfo) } returns weather

        val result = weatherRepository.getWeatherById(cityId)

        assertEquals(weather, result)
    }

    @Test
    fun testGetNearCities() = runBlocking {
        val latitude = 37.7749
        val longitude = -122.4194
        val count = 5
        val weatherList = mockk<WeatherList>()
        val cities = mockk<Cities>()

        coEvery { weatherApi.getNearCities(latitude, longitude, count) } returns weatherList
        coEvery { weatherMapper.toListWeather(weatherList) } returns cities

        val result = weatherRepository.getNearCities(latitude, longitude, count)

        assertEquals(cities, result)
    }
}

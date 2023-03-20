package com.example.weather.data.api

import com.example.weather.data.api.model.WeatherInfo
import com.example.weather.data.api.model.WeatherList
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?")
    suspend fun getWeatherByName(@Query("q") city: String): WeatherInfo

    @GET("find?")
    suspend fun getNearCities(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int
    ): WeatherList

    @GET("weather?")
    suspend fun getWeatherById(@Query("id") id: Int): WeatherInfo
}
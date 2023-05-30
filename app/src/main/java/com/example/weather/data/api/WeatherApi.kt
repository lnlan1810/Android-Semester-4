package com.example.weather.data.api

import com.example.weather.data.api.model.WeatherInfo
import com.example.weather.data.api.model.WeatherList
import com.example.weather.data.api.response.CitiesResponse
import com.example.weather.data.api.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?")
    suspend fun getWeatherByName(@Query("q") city: String): WeatherResponse

    @GET("find?")
    suspend fun getNearCities(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int
    ): CitiesResponse

    @GET("weather?")
    suspend fun getWeatherById(@Query("id") id: Int): WeatherResponse
}
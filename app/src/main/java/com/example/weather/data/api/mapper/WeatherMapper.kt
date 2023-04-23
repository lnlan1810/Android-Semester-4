package com.example.weather.data.api.mapper

import com.example.weather.data.api.model.WeatherList
import com.example.weather.data.api.model.WeatherInfo
import com.example.weather.domain.converter.CountryNameConverter
import com.example.weather.domain.converter.DateConverter
import com.example.weather.domain.converter.WindConverter
import com.example.weather.domain.entity.Cities
import com.example.weather.domain.entity.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor(
    private val windConverter: WindConverter,
    private val dateConverter: DateConverter,
    private val countryNameConverter: CountryNameConverter
) {
    fun toWeather(response: WeatherInfo): Weather = Weather(
        id = response.id,
        name = response.name,
        countryName = countryNameConverter.getCountryName(response.coord.lat, response.coord.lon),
        latitude = response.coord.lat,
        longitude = response.coord.lon,
        temp = response.main.temp,
        icon = response.weather[0].icon,
        desc = response.weather[0].description,
        feelsLike = response.main.feelsLike,
        humidity = response.main.humidity,
        windSpeed = response.wind.speed,
        windDir = windConverter.convertWindDir(response.wind.deg),
        date = dateConverter.convertDate(null, response.timezone),
        sunrise = dateConverter.convertDate(response.sys.sunrise, response.timezone),
        sunset = dateConverter.convertDate(response.sys.sunset, response.timezone)
    )

    fun toListWeather(response: WeatherList): Cities = Cities(
        list = response.weatherList.map { weatherInfo -> toWeather(weatherInfo) }
    )
}

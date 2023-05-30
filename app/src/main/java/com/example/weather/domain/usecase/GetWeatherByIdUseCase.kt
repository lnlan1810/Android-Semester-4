package com.example.weather.domain.usecase

import com.example.weather.data.api.response.WeatherResponse
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherByIdUseCase (
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(
        id: Int
    ): WeatherResponse = weatherRepository.getWeatherById(id)
}

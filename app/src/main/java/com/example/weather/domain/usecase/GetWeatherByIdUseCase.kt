package com.example.weather.domain.usecase

import com.example.weather.domain.entity.Weather
import com.example.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherByIdUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(
        id: Int
    ): Weather {
        return withContext(dispatcher) {
            weatherRepository.getWeatherById(
                id
            )
        }
    }
}

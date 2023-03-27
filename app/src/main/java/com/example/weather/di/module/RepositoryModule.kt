package com.example.weather.di.module

import com.example.weather.data.api.WeatherRepositoryImpl
import com.example.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module


@Module
interface RepositoryModule {
    @Binds
    fun weatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}

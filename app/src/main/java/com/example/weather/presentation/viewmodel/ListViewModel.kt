package com.example.weather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.usecase.GetNearCitiesUseCase
import com.example.weather.domain.usecase.GetWeatherByNameUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase,
    private val getNearCitiesUseCase: GetNearCitiesUseCase,
) : ViewModel() {

    private var _cities: MutableLiveData<Result<List<Weather>>> = MutableLiveData()
    //view can't change livedata
    val cities: LiveData<Result<List<Weather>>> = _cities

    fun onGetNearCitiesClick(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val cities = getNearCitiesUseCase(latitude, longitude, 10).list
                _cities.value = Result.success(cities)
            } catch (ex: Exception) {
                _cities.value = Result.failure(ex)
            }
        }
    }

    private var _weather: SingleLiveEvent<Result<Weather>> = SingleLiveEvent<Result<Weather>>()
    val weather: SingleLiveEvent<Result<Weather>> = _weather

    fun onGetWeatherByNameClick(cityName: String) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByNameUseCase(cityName)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
            }
        }
    }
}


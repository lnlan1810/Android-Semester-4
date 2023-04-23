package com.example.weather.presentation.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weather.data.api.response.WeatherResponse
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.usecase.GetNearCitiesUseCase
import com.example.weather.domain.usecase.GetWeatherByNameUseCase
import com.example.weather.presentation.recyclerview.models.CityItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase,
    private val getNearCitiesUseCase: GetNearCitiesUseCase,
) : ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherResponse?>(null)
    val weatherResponse: LiveData<WeatherResponse?>
        get() = _weatherResponse

    private val _citiesList = MutableLiveData<List<CityItem>?>(null)
    val citiesList: LiveData<List<CityItem>?>
        get() = _citiesList

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    fun onSearchClick(cityName: String) {
        loadWeather(cityName)
    }

    private fun loadWeather(cityName: String) {
        viewModelScope.launch {
            getWeatherByNameUseCase(cityName).also {
                _weatherResponse.value = it
            }
        }
    }


//    private fun loadWeatherResult(cityName: String) {
//        viewModelScope.launch {
//            try {
//                getWeatherByNameUseCase(cityName).also {
//                    _weatherResponse.value = Result.success(it)
//                }
//            } catch (ex: Throwable) {
//                _weatherResponse.value = Result.failure(ex)
//                _error.value = ex
//            }
//        }
//    }

    fun onGetNearCitiesClick(
        latitude: Double,
        longitude: Double,
    ) {
        viewModelScope.launch {
            getNearCitiesUseCase(latitude, longitude, 10).also {
                _citiesList.value = it
            }
        }
    }

    fun onGetWeatherByNameClick(cityName: String) {
        viewModelScope.launch {
            getWeatherByNameUseCase(cityName).also {
                _weatherResponse.value = it
            }
        }
    }
}

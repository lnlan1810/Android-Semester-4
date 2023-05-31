package com.example.weather.presentation.viewmodel

import com.example.weather.domain.entity.Weather
import com.example.weather.domain.usecase.GetWeatherByIdUseCase
import androidx.lifecycle.Observer
import com.example.weather.presentation.utils.getOrAwaitValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test
import org.junit.rules.TestRule

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class InfoViewModelTest {

    @MockK
    lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase

    private lateinit var viewModel: InfoViewModel

    private val expectedValue = 1;

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = InfoViewModel(getWeatherByIdUseCase, expectedValue)
    }

    @Test
    fun onGetWeatherByIdClick() = runBlocking {
        //arrange
        val mockWeather = mockk<Weather> { every {id} returns expectedValue}

        coEvery { getWeatherByIdUseCase.invoke(expectedValue) } returns mockWeather

        //act
        viewModel.onGetWeatherByIdClick()

        //assert
        assertEquals(Result.success(mockWeather), viewModel.weather.getOrAwaitValue())
    }
}
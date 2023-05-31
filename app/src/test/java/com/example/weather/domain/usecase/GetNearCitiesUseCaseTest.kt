package com.example.weather.domain.usecase

import com.example.weather.domain.entity.Cities
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.presentation.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetNearCitiesUseCaseTest {

    @MockK
    lateinit var repository: WeatherRepository

    @get:Rule
    val coroutineRule: MainCoroutineRule = MainCoroutineRule()

    private lateinit var useCase: GetNearCitiesUseCase

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetNearCitiesUseCase(repository, coroutineRule.testDispatcher)
    }

    @Test
    operator fun invoke() = runBlocking {
        //arrange
        val mockList = arrayListOf<Weather>(
            mockk { every { id } returns 1 },
            mockk { every { id } returns 2 },
        )
        val expectedWeatherList = mockk<Cities> { every { list } returns mockList }

        coEvery { repository.getNearCities(11.0, 13.0, 2) } returns expectedWeatherList

        //act
        val result = useCase.invoke(11.0, 13.0, 2)

        //assert
        kotlin.test.assertEquals(mockList, result.list)
    }
}
package com.example.weather.presentation.fragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherDetailBinding
import com.example.weather.domain.entity.Weather
import com.example.weather.presentation.viewmodel.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CityWeatherFragment : Fragment(R.layout.fragment_weather_detail) {

    private lateinit var binding: FragmentWeatherDetailBinding
    private lateinit var glide: RequestManager

    private val idCity: Int by lazy {
        arguments?.getInt("idCity") ?: -1
    }

    @Inject
    lateinit var viewModelFactory: InfoViewModel.WeatherViewModelFactory

    private val viewModel: InfoViewModel by viewModels {
        InfoViewModel.provideFactory(viewModelFactory, idCity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailBinding.bind(view)
        initObservers()

        glide = Glide.with(this)
        viewModel.onGetWeatherByIdClick()
    }

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = { city ->
                setData(city)
            },
                onFailure = {
                    Timber.e(it.message.toString())
                })
        }
    }

    private fun setData(city: Weather) {
        binding.city = city
        with(binding) {
            glide.load("http://openweathermap.org/img/wn/${city.icon}@2x.png")
                .into(ivWeather)
        }
    }
}

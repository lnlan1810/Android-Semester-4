package com.example.weather.presentation.fragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.weather.App
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherDetailBinding
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.usecase.GetWeatherByIdUseCase
import com.example.weather.presentation.viewmodel.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_weather.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt


class CityWeatherFragment : Fragment(R.layout.fragment_weather_detail) {

    private lateinit var binding: FragmentWeatherDetailBinding
    private lateinit var glide: RequestManager
    private var cityId: Int = 0

    private val idCity: Int by lazy {
        arguments?.getInt("idCity") ?: -1
    }

    @Inject
    lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase

    private val viewModel: InfoViewModel by viewModels {
        InfoViewModel.provideFactory(getWeatherByIdUseCase)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailBinding.bind(view)
        arguments?.getString(CITY_ID_TAG).also {
            if (it != null) {
                cityId = it.toInt()
                viewModel.loadWeather(cityId)
            }
        }
        setView()
    }

    private fun setView() {
        viewModel.weatherResponse.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            binding?.run {
                tvCity.text = it.name
                tvTemp.text = "${it.main.temp.roundToInt()}°"
                tvSunrise.text = viewModel.getTime(it.sys.sunrise)
                tvSunset.text= viewModel.getTime(it.sys.sunset)
                tvHumidity.text = "${it.main.humidity}%"
                tvWind.text = viewModel.windConverter(it.wind.deg)
                tvRealfeel.text ="Real feel: ${it.main.feelsLike.roundToInt()}°"

                tvCountry.text = it.sys.country
                glide.load("https://openweathermap.org/img/w/${it.weather[0].icon}.png") .into(ivWeather)
            }
        }
    }

    private fun setData(city: Weather) {
        binding.city = city
        with(binding) {
            glide.load("http://openweathermap.org/img/wn/${city.icon}@2x.png")
                .into(ivWeather)
        }
    }
    companion object {
        const val CITY_INFO_FRAGMENT_TAG = "CITY_INFO_FRAGMENT_TAG"
        const val CITY_ID_TAG = "CITY_ID_TAG"

        fun getInstance(message: String) =
            CityWeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(CITY_ID_TAG, message)
                }
            }
    }

}

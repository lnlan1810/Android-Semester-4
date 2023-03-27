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
import com.example.weather.presentation.activity.MainActivity
import com.example.weather.presentation.viewmodel.InfoViewModel
import com.example.weather.utils.factory.ViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class CityWeatherFragment : Fragment(R.layout.fragment_weather_detail) {

    private lateinit var binding: FragmentWeatherDetailBinding
    private lateinit var glide: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: InfoViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailBinding.bind(view)
        initObservers()

        val idCity = arguments?.getInt("idCity")
        glide = Glide.with(this)
        idCity?.let {
            viewModel.onGetWeatherByIdClick(it)
        }
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

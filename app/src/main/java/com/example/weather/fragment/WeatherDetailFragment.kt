package com.example.weather.fragment

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.weather.R
import com.example.weather.data.api.WeatherRepository
import com.example.weather.data.model.info.WeatherInfo
import com.example.weather.databinding.FragmentWeatherDetailBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class WeatherDetailFragment : Fragment() {

    private lateinit var binding: FragmentWeatherDetailBinding
    private lateinit var city: WeatherInfo
    private lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idCity = arguments?.getInt("idCity")
        glide = Glide.with(this)
        idCity?.let {
            getData(idCity)
        }
    }

    private fun getData(idCity: Int) {
        lifecycleScope.launch {
            try {
                city = repository.getWeatherById(idCity)
                setData(city)
            } catch (ex: HttpException) {
                Timber.e(ex.message.toString())
            }
        }
    }

    private fun setData(city: WeatherInfo) {
        with(binding) {
            tvCity.text = city.name

            val gcd = Geocoder(context)
            val addresses = gcd.getFromLocation(city.coord.lat, city.coord.lon, 1);

            if (addresses.size > 0) {
                val countryName = addresses[0].countryName
                tvCountry.text = countryName
            }
            glide.load("http://openweathermap.org/img/wn/${city.weather[0].icon}@2x.png")
                .into(ivWeather)
            tvTemp.text = resources.getString(R.string.tv_temp, city.main.temp)
            tvWeather.text = city.weather[0].description
            tvHumidity.text = "${city.main.humidity}%"
            tvWind.text = resources.getString(R.string.tv_wind, city.wind.speed)
            tvDirection.text = when (city.wind.deg) {
                in 0..22 -> "N"
                in 23..67 -> "NE"
                in 68..112 -> "E"
                in 113..157 -> "SE"
                in 158..202 -> "S"
                in 203..247 -> "SW"
                in 248..292 -> "W"
                in 293..337 -> "NW"
                in 337..360 -> "N"
                else -> "Not found"
            }
            val formatter = SimpleDateFormat("HH:mm")

            var date = Date((city.sys.sunrise + city.timezone) * 1000.toLong())
            tvSunrise.text = formatter.format(date)

            date = Date((city.sys.sunset + city.timezone) * 1000.toLong())
            tvSunset.text = formatter.format(date)


            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val dateInUtc = Date()
            date = Date(dateInUtc.time + city.timezone * 1000.toLong())
           // tvCurrentTime.text = formatter.format(date)
        }
    }

    private val repository by lazy {
        WeatherRepository()
    }


}

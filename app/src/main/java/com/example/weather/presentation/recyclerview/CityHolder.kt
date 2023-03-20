package com.example.weather.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.weather.R
import com.example.weather.databinding.ItemWeatherBinding
import com.example.weather.domain.entity.Weather

class CityHolder(
    private val binding: ItemWeatherBinding,
    private val glide: RequestManager,
    private val selectCity: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var city: Weather? = null

    init{
        itemView.setOnClickListener {
            city?.id?.also(selectCity)
        }
    }

    fun bind(item: Weather) {
        city = item
        with(binding) {
            glide.load("http://openweathermap.org/img/wn/${item.icon}@2x.png")
                .into(ivSmallWeather)
            tvName.text = item.name
            val temp = String.format("%.1f", item.temp)
            tvTemp.text = "${temp}°C"
            //tvTemp.text = Resources.getSystem().getString(R.string.tv_temp, item.main.temp)

            when (item.temp) {
                in 30.0..40.0 -> setColor(R.color.temp_40)
                in 20.0..30.0 -> setColor(R.color.temp_30)
                in 15.0..20.0 -> setColor(R.color.temp_20)
                in 10.0..15.0 -> setColor(R.color.temp_15)
                in 0.0..10.0 -> setColor(R.color.temp_10)
                in -5.0..0.0 -> setColor(R.color.temp_0)
                in -15.0..-5.0 -> setColor(R.color.temp_minus_5)
                in -25.0..-15.0 -> setColor(R.color.temp_minus_15)
                in -35.0..-25.0 -> setColor(R.color.temp_minus_25)
                in -45.0..-35.0 -> setColor(R.color.temp_minus_35)
            }
        }
    }

    private fun setColor(id: Int) {
        binding.tvTemp.setTextColor(
            ContextCompat.getColor(
                binding.tvTemp.context,
                id
            )
        )
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            selectCity: (Int) -> Unit
        ) = CityHolder(
            ItemWeatherBinding.inflate(
                LayoutInflater
                    .from(parent.context),
                parent,
                false
            ),
            glide,
            selectCity
        )
    }
}

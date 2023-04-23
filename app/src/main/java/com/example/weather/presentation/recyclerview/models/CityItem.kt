package com.example.weather.presentation.recyclerview.models

import com.example.weather.R

data class CityItem(
    val id: Int,
    val name: String,
    val temp: Int,
    val url: String
) {

    fun tempColor(): Int {
        return when (this.temp) {
            in -1000..-30 -> R.color.temp_minus_35
            in -29..-1 -> R.color.temp_minus_15
            in 0..5 -> R.color.temp_0
            in 6..20 -> R.color.temp_15
            in 21..1000 -> R.color.temp_10
            else -> R.color.black
        }
    }
}


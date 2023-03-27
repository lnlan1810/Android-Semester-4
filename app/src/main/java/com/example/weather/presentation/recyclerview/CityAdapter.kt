package com.example.weather.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.weather.R
import com.example.weather.databinding.ItemWeatherBinding
import com.example.weather.domain.entity.Weather

class CityAdapter(
    private val list: ArrayList<Weather>,
    private val glide: RequestManager,
    private val selectCity: (Int) -> Unit
) : RecyclerView.Adapter<CityHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder = CityHolder.create(parent, glide, selectCity)

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

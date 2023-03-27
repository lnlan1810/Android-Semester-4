package com.example.weather.domain.converter

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateConverter @Inject constructor(){
    @SuppressLint("SimpleDateFormat")
    fun convertDate(dateInSeconds: Int?, timezone: Int): String {
        val formatter = SimpleDateFormat("HH:mm")
        dateInSeconds?.let {
            val date = Date((dateInSeconds + timezone) * 1000.toLong())
            return formatter.format(date)
        }
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date(Date().time + timezone * 1000.toLong())
        return formatter.format(date)
    }
}
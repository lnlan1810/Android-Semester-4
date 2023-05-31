package com.example.weather.domain.converter

import android.content.Context
import android.location.Geocoder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CountryNameConverter @Inject constructor(
    @ApplicationContext private var context: Context
) {
    fun getCountryName(latitude: Double, longitude: Double): String {
        val gcd = Geocoder(context)
        val addresses = gcd.getFromLocation(latitude, longitude, 1)
        if (addresses.size > 0) {
            return addresses[0].countryName
        }
        return ""
    }
}

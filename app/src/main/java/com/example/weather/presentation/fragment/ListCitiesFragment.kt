package com.example.weather.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherListBinding
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.usecase.GetNearCitiesUseCase
import com.example.weather.domain.usecase.GetWeatherByNameUseCase
import com.example.weather.presentation.recyclerview.CityAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


private const val COUNT_CITY = 10

class ListCitiesFragment : Fragment() {
    private lateinit var binding: FragmentWeatherListBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cities: List<Weather>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var getWeatherByNameUseCase: GetWeatherByNameUseCase
    private lateinit var getNearCitiesUseCase: GetNearCitiesUseCase

    //Moscow as default city
    private var latitude: Double = 55.644466
    private var longitude: Double = 37.395744

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.svCity.queryHint = "type a city"
        getLocation()
        searchCity()
    }

    private fun getList() {
        lifecycleScope.launch {
            try {
                cities = getNearCitiesUseCase(latitude, longitude, COUNT_CITY).list
                context?.let{ context ->
                    cityAdapter = CityAdapter(cities as ArrayList<Weather>, Glide.with(context)) {city ->
                        navigateToWeatherDetails(city)
                    }
                    binding.rvWeather.adapter = cityAdapter
                }
            } catch (ex: HttpException) {
                Log.e("всё плохо", ex.message.toString())
            }
        }
    }

    private fun navigateToWeatherDetails(idCity: Int) {
        val bundle = Bundle().apply {
            putInt("idCity", idCity)
        }
        findNavController().navigate(R.id.action_weatherListFragment_to_weatherDetailFragment, bundle)
    }

    private fun searchCity() {
        binding.svCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(cityName: String): Boolean {
                lifecycleScope.launch {
                    try {
                        val id = getWeatherByNameUseCase(cityName).id
                        navigateToWeatherDetails(id)
                    } catch (ex: HttpException) {
                        Snackbar.make(
                            binding.root,
                            "City Not Found",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                return false
            }
        })
    }


    private fun getLocation() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }
                getList()
            }
        } else {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            requestPermissions(permissions, 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    Snackbar.make(binding.root, "access denied", Snackbar.LENGTH_SHORT)
                        .show()
                    getList()
                }
            }
        }
    }
}


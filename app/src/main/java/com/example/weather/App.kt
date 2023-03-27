package com.example.weather

import android.app.Application
import com.example.weather.di.AppComponent
import com.example.weather.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()
    }
}
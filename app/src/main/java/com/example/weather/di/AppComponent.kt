package com.example.weather.di

import com.example.weather.App
import com.example.weather.di.module.AppModule
import com.example.weather.di.module.NetModule
import com.example.weather.di.module.RepositoryModule
import com.example.weather.di.module.viewmodel.ViewModelModule
import com.example.weather.presentation.activity.MainActivity
import com.example.weather.presentation.fragment.CityWeatherFragment
import com.example.weather.presentation.fragment.ListCitiesFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(cityWeatherFragment: CityWeatherFragment)
    fun inject(listCitiesFragment: ListCitiesFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(application: App): Builder

        fun build(): AppComponent
    }
}
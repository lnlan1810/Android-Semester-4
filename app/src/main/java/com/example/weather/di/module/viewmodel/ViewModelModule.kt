package com.example.weather.di.module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.di.ViewModelKey
import com.example.weather.presentation.viewmodel.InfoViewModel
import com.example.weather.presentation.viewmodel.ListViewModel
import com.example.weather.utils.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    fun bindInfoViewModel(
        infoViewModel: InfoViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    fun bindListViewModel(
        infoViewModel: ListViewModel
    ): ViewModel
}

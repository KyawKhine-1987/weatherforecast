package com.freelance.android.weatherforecastmvvm.ui.weather.current

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freelance.android.weatherforecastmvvm.data.provider.UnitProvider
import com.freelance.android.weatherforecastmvvm.data.repository.ForecastRepository

/**
 * Created by KyawKhine on 01/14/2019 11:20 AM.
 */


class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    private val LOG_TAG = CurrentWeatherViewModelFactory::class.java.name

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.i(LOG_TAG, "TEST: create() called...")

        return CurrentWeatherViewModel(forecastRepository, unitProvider) as T
    }
}
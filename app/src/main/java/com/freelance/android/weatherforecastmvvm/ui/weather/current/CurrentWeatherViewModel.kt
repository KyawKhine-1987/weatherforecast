package com.freelance.android.weatherforecastmvvm.ui.weather.current

import android.util.Log
import com.freelance.android.weatherforecastmvvm.data.provider.UnitProvider
import com.freelance.android.weatherforecastmvvm.data.repository.ForecastRepository
import com.freelance.android.weatherforecastmvvm.internal.lazyDeferred
import com.freelance.android.weatherforecastmvvm.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    private val LOG_TAG = CurrentWeatherViewModel::class.java.name
    //private val unitSystem = UnitSystem.METRIC //get from settings later
    /*private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC*/

    //this method called from Delegates.kt
    val weather by lazyDeferred {
        Log.i(LOG_TAG, "TEST: weather() called...")

        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
    /*Coroutines by another name
      The pattern of async and await in other languages is based on coroutines. If you're familiar with this pattern, the suspend keyword is similar to async. However in Kotlin, await() is implicit when calling a suspend function.

      Kotlin has a method Deferred.await() that is used to wait for the result from a coroutine started with the async builder.*/

    /*val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }*/
}

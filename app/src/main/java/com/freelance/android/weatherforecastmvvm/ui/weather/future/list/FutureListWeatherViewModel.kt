package com.freelance.android.weatherforecastmvvm.ui.weather.future.list

import android.util.Log
import com.freelance.android.weatherforecastmvvm.data.provider.UnitProvider
import com.freelance.android.weatherforecastmvvm.data.repository.ForecastRepository
import com.freelance.android.weatherforecastmvvm.internal.lazyDeferred
import com.freelance.android.weatherforecastmvvm.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    private val LOG_TAG = FutureListWeatherViewModel::class.java.name

    val weatherEntries by lazyDeferred{
        Log.i(LOG_TAG, "TEST: weatherEntries() called...")

        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
    }
}

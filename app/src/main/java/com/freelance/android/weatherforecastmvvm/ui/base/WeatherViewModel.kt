package com.freelance.android.weatherforecastmvvm.ui.base

import androidx.lifecycle.ViewModel
import com.freelance.android.weatherforecastmvvm.data.provider.UnitProvider
import com.freelance.android.weatherforecastmvvm.data.repository.ForecastRepository
import com.freelance.android.weatherforecastmvvm.internal.UnitSystem
import com.freelance.android.weatherforecastmvvm.internal.lazyDeferred

/**
 * Created by KyawKhine on 01/16/2019 4:09 PM.
 */


abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}
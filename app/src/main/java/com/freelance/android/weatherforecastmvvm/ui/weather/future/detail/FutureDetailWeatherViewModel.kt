package com.freelance.android.weatherforecastmvvm.ui.weather.future.detail

import com.freelance.android.weatherforecastmvvm.data.provider.UnitProvider
import com.freelance.android.weatherforecastmvvm.data.repository.ForecastRepository
import com.freelance.android.weatherforecastmvvm.internal.lazyDeferred
import com.freelance.android.weatherforecastmvvm.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    private val LOG_TAG = FutureDetailWeatherViewModel::class.java.name

    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }

}

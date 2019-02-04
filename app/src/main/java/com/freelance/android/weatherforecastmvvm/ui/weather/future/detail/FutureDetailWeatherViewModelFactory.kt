package com.freelance.android.weatherforecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freelance.android.weatherforecastmvvm.data.provider.UnitProvider
import com.freelance.android.weatherforecastmvvm.data.repository.ForecastRepository
import org.threeten.bp.LocalDate

/**
 * Created by KyawKhine on 02/04/2019 4:56 PM.
 */


class FutureDetailWeatherViewModelFactory(private val detailDate: LocalDate,
                                          private val forecastRepository: ForecastRepository,
                                          private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(detailDate, forecastRepository, unitProvider) as T
    }
}
package com.freelance.android.weatherforecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.freelance.android.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

/**
 * Created by KyawKhine on 01/13/2019 1:31 PM.
 */


interface ForecastRepository {
    //"out" keyword solved the generic problem.
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> // suspend feature from Coroutines.

    suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>

    suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<out UnitSpecificDetailFutureWeatherEntry>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>

}
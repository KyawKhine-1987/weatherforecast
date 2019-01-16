package com.freelance.android.weatherforecastmvvm.data.provider

import com.freelance.android.weatherforecastmvvm.data.db.entity.WeatherLocation

/**
 * Created by KyawKhine on 01/15/2019 1:05 PM.
 */


interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation) : Boolean
    suspend fun getPreferredLocationString() : String
}
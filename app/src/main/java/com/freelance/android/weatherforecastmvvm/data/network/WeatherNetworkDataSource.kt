package com.freelance.android.weatherforecastmvvm.data.network

import androidx.lifecycle.LiveData
import com.freelance.android.weatherforecastmvvm.data.network.response.CurrentWeatherResponse
import com.freelance.android.weatherforecastmvvm.data.network.response.FutureWeatherResponse

/**
 * Created by KyawKhine on 01/12/2019 9:12 AM.
 */


interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        //days: Int,
        languageCode: String
    )
}
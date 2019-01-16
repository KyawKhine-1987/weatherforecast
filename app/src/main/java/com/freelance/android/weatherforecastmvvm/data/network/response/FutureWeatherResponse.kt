package com.freelance.android.weatherforecastmvvm.data.network.response

import com.freelance.android.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(

    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDaysContainer,
    val location: WeatherLocation
)
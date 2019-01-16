package com.freelance.android.weatherforecastmvvm.data.network.response

import com.freelance.android.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(

    val location: WeatherLocation,

    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)
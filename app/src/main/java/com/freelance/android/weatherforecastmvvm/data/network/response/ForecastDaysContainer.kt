package com.freelance.android.weatherforecastmvvm.data.network.response

import com.freelance.android.weatherforecastmvvm.data.db.entity.FutureWeatherEntry
import com.google.gson.annotations.SerializedName

data class ForecastDaysContainer(

    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)
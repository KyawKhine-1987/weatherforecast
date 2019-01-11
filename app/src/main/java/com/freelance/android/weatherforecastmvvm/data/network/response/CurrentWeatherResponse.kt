package com.freelance.android.weatherforecastmvvm.data.response

import com.freelance.android.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.entity.Location
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(

    val location: Location,

    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)
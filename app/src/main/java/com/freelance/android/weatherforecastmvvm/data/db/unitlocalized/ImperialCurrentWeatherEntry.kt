package com.freelance.android.weatherforecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

/**
 * Created by KyawKhine on 01/11/2019 3:50 PM.
 */

//Just "class" which is included properties.
class   ImperialCurrentWeatherEntry(
    @ColumnInfo(name = "tempF")
    override val temperature: Double,

    @ColumnInfo(name = "condition_text")
    override val conditionText: String,

    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,

    @ColumnInfo(name = "windMph")
    override val windSpeed: Double,

    @ColumnInfo(name = "windDir")
    override val windDirection: String,

    @ColumnInfo(name = "precipIn")
    override val precipitationVolume: Double,

    @ColumnInfo(name = "feelslikeF")
    override val feelsLikeTemprature: Double,

    @ColumnInfo(name = "visMiles")
    override val visibilityDistance: Double

) : UnitSpecificCurrentWeatherEntry
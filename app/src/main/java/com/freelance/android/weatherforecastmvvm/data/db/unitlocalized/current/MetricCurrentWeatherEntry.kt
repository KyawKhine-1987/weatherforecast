package com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.current

import androidx.room.ColumnInfo

/**
 * Created by KyawKhine on 01/11/2019 3:56 PM.
 */

//Just "class" which is included properties.
data class MetricCurrentWeatherEntry(
    @ColumnInfo(name = "tempC")
    override val temperature: Double,

    @ColumnInfo(name = "condition_text")
    override val conditionText: String,

    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,

    @ColumnInfo(name = "windKph")
    override val windSpeed: Double,

    @ColumnInfo(name = "windDir")
    override val windDirection: String,

    @ColumnInfo(name = "precipMm")
    override val precipitationVolume: Double,

    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemprature: Double,

    @ColumnInfo(name = "visKm")
    override val visibilityDistance: Double

) : UnitSpecificCurrentWeatherEntry
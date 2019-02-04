package com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.detail

import org.threeten.bp.LocalDate

/**
 * Created by KyawKhine on 02/04/2019 2:22 PM.
 */


interface UnitSpecificDetailFutureWeatherEntry {
    val date: LocalDate
    val maxTemperature: Double
    val minTemperature: Double
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val maxWindSpeed: Double
    val totalPrecipitation: Double
    val avgVisibilityDistance: Double
    val uv: Double
}
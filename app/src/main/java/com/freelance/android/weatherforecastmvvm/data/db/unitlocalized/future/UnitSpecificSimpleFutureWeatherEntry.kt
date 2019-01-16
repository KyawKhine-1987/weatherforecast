package com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future

import org.threeten.bp.LocalDate

/**
 * Created by KyawKhine on 01/16/2019 1:47 PM.
 */


interface UnitSpecificSimpleFutureWeatherEntry {

    val date: LocalDate //it was used LocalDateConverter::class
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
}
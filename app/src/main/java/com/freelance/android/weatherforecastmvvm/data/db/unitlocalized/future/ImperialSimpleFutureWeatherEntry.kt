package com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

/**
 * Created by KyawKhine on 01/16/2019 1:50 PM.
 */


class ImperialSimpleFutureWeatherEntry(

    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempF")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String
) : UnitSpecificSimpleFutureWeatherEntry
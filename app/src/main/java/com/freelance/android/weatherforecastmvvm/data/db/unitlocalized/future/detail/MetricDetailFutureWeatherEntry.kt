package com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.detail

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

/**
 * Created by KyawKhine on 02/04/2019 2:58 PM.
 */


data class MetricDetailFutureWeatherEntry (
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "maxtempC")//Mind out! Now, fix it and in the past it's fetched Imperial temperature data error.
    override val maxTemperature: Double,
    @ColumnInfo(name = "mintempC")
    override val minTemperature: Double,
    @ColumnInfo(name = "avgtempC")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "maxwindKph")//Mind out! it's fetched Mph data error.
    override val maxWindSpeed: Double,
    @ColumnInfo(name = "totalprecipMm")//Mind out! it's fetched In data error.
    override val totalPrecipitation: Double,
    @ColumnInfo(name = "avgvisKm")//Mind out! it's fetched Miles data error.
    override val avgVisibilityDistance: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double
) : UnitSpecificDetailFutureWeatherEntry
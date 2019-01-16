package com.freelance.android.weatherforecastmvvm.data.db

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by KyawKhine on 01/16/2019 2:04 PM.
 */


object LocalDateConverter {

    @TypeConverter
    @JvmStatic
    // "?" isn't null value.
    fun stringToDate(str: String?) = str?.let {
        LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    @JvmStatic
    // "?" isn't null value.
    fun dateToString(dateTime: LocalDate?) = dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE)
}
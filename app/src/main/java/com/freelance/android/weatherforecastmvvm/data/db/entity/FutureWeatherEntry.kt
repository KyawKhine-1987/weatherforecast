package com.freelance.android.weatherforecastmvvm.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(

    @PrimaryKey(autoGenerate = true)
    val id: Int ?= null,
    val date: String, //it was used LocalDateConverter::class

    @Embedded
    val day: Day
)
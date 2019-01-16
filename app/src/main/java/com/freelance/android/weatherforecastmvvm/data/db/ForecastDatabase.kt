package com.freelance.android.weatherforecastmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.freelance.android.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.entity.FutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.entity.WeatherLocation

/**
 * Created by KyawKhine on 01/11/2019 4:10 PM.
 */

@Database(
    entities = [CurrentWeatherEntry::class, FutureWeatherEntry::class, WeatherLocation::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun futureWeatherDao(): FutureWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
        @Volatile
        private var instance: ForecastDatabase? = null
        private val LOCK = Any()// LOCK is dummy object.

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java, "forecast.db")
                .build()
    }
}
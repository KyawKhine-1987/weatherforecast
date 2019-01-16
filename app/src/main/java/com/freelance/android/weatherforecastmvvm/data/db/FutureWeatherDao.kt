package com.freelance.android.weatherforecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freelance.android.weatherforecastmvvm.data.db.entity.FutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.ImperialSimpleFutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

/**
 * Created by KyawKhine on 01/16/2019 2:14 PM.
 */

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("select * from future_weather where date(date) >= date(:startDate)")// This both "date()" are sqlite function    and "(:startDate)" parameter from "(startDate: LocalDate)" and "(date)" parentisic is from FutureWeatherEntry.kt.
    fun getSimpleWeatherForecastMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from future_weather where date(date) <= date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)
}
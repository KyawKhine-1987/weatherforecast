package com.freelance.android.weatherforecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freelance.android.weatherforecastmvvm.data.db.entity.CURRENT_WEATHER_ID
import com.freelance.android.weatherforecastmvvm.data.db.entity.CurrentWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.current.ImperialCurrentWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.current.MetricCurrentWeatherEntry

/**
 * Created by KyawKhine on 01/11/2019 4:02 PM.
 */

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)//upsert means insert and update can do both.

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}
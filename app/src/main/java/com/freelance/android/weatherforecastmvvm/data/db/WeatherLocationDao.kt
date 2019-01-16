package com.freelance.android.weatherforecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freelance.android.weatherforecastmvvm.data.db.entity.WEATHER_LOCATION_ID
import com.freelance.android.weatherforecastmvvm.data.db.entity.WeatherLocation

/**
 * Created by KyawKhine on 01/15/2019 12:39 PM.
 */

@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: WeatherLocation)//upsert means insert and update can do both.

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocationNonLive(): WeatherLocation?

}
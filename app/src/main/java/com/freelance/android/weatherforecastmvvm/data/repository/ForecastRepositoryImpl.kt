package com.freelance.android.weatherforecastmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.freelance.android.weatherforecastmvvm.data.db.CurrentWeatherDao
import com.freelance.android.weatherforecastmvvm.data.db.FutureWeatherDao
import com.freelance.android.weatherforecastmvvm.data.db.WeatherLocationDao
import com.freelance.android.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.network.FORECAST_DAYS_COUNT
import com.freelance.android.weatherforecastmvvm.data.network.WeatherNetworkDataSource
import com.freelance.android.weatherforecastmvvm.data.network.response.CurrentWeatherResponse
import com.freelance.android.weatherforecastmvvm.data.network.response.FutureWeatherResponse
import com.freelance.android.weatherforecastmvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import java.time.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(

    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    private val LOG_TAG = ForecastRepositoryImpl::class.java.name

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                //persist
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                //persist
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        Log.i(LOG_TAG, "TEST: getCurrentWeather() called...")

        return withContext(Dispatchers.IO) {

            initWeatherData()
            return@withContext if (metric) {
                currentWeatherDao.getWeatherMetric()
            } else {
                currentWeatherDao.getWeatherImperial()
            }
        }
    }

    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        Log.i(LOG_TAG, "TEST: getFutureWeatherList() called...")

        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getSimpleWeatherForecastMetric(startDate)
            else futureWeatherDao.getSimpleWeatherForecastImperial(startDate)
        }
    }

    override suspend fun getFutureWeatherByDate(
        date: LocalDate,
        metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry> {
        Log.i(LOG_TAG, "TEST: getFutureWeatherByDate() called...")

        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getDetailWeatherByDateMetric(date)
            else futureWeatherDao.getDetailWeatherByDateImperial(date)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        Log.i(LOG_TAG, "TEST: getWeatherLocation() called...")

        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        Log.i(LOG_TAG, "TEST: persistFetchedCurrentWeather() called...")

        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {
        Log.i(LOG_TAG, "TEST: persistFetchedFutureWeather() called...")

        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        Log.i(LOG_TAG, "TEST: initWeatherData() called...")

        /*if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchCurrentWeather()
        }*/
        /*val lastWeatherLocation = weatherLocationDao.getLocation().value*/
        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)) fetchCurrentWeather()

        if (isFetchFutureNeeded()) fetchFutureWeather()
    }

    private suspend fun fetchCurrentWeather() {
        Log.i(LOG_TAG, "TEST: fetchCurrentWeather() called...")

        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private suspend fun fetchFutureWeather() {
        Log.i(LOG_TAG, "TEST: fetchFutureWeather() called...")

        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        Log.i(LOG_TAG, "TEST: isFetchCurrentNeeded() called...")

        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchFutureNeeded(): Boolean {
        Log.i(LOG_TAG, "TEST: isFetchFutureNeeded() called...")

        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }
}
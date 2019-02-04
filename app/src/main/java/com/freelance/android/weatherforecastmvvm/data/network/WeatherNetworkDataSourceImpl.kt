package com.freelance.android.weatherforecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freelance.android.weatherforecastmvvm.data.network.response.CurrentWeatherResponse
import com.freelance.android.weatherforecastmvvm.data.network.response.FutureWeatherResponse
import com.freelance.android.weatherforecastmvvm.internal.NoConnectivityException

const val FORECAST_DAYS_COUNT = 7

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

    private val LOG_TAG = WeatherNetworkDataSourceImpl::class.java.name

    private val _downloadedCurrentWeather =
        MutableLiveData<CurrentWeatherResponse>() //It is used to Encapsulation Method.
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        Log.i(LOG_TAG, "TEST: fetchCurrentWeather() called...")

        try {
            val fetchedCurrentWeather = apixuWeatherApiService.getCurrentWeather(location, languageCode).await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e(LOG_TAG + "Connectivity", "No Internet Connection!", e)
        }
    }

    private val _downloadedFutureWeather =
        MutableLiveData<FutureWeatherResponse>() //It is used to Encapsulation Method.
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(location: String, languageCode: String) {
        Log.i(LOG_TAG, "TEST: fetchFutureWeather() called...")

        try {

            val fetchedFutureWeather = apixuWeatherApiService.getFutureWeather(location, FORECAST_DAYS_COUNT, languageCode).await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoConnectivityException) {
            Log.e(LOG_TAG + "Connectivity", "No Internet Connection!", e)
        }
    }
}
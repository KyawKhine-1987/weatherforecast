package com.freelance.android.weatherforecastmvvm.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import com.freelance.android.weatherforecastmvvm.data.db.entity.WeatherLocation
import com.freelance.android.weatherforecastmvvm.internal.LocationPermissionNotGrantedException
import com.freelance.android.weatherforecastmvvm.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    private val LOG_TAG = LocationProviderImpl::class.java.name
    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        Log.i(LOG_TAG, "TEST: hasLocationChanged() called...")

        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String {
        Log.i(LOG_TAG, "TEST: getPreferredLocationString() called...")

        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocation().await() ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude}, ${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException) {
                return "${getCustomLocationName()}"
            }
        } else
            return "${getCustomLocationName()}"
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        Log.i(LOG_TAG, "TEST: hasDeviceLocationChanged() called...")

        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false // if deviceLocation is equal to null value then return false. "?:" means null symbol.

        // Comparing doubles cannot be done with "=="
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        Log.i(LOG_TAG, "TEST: hasCustomLocationChanged() called...")

        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    private fun isUsingDeviceLocation(): Boolean {
        Log.i(LOG_TAG, "TEST: isUsingDeviceLocation() called...")

        return preferences.getBoolean(
            USE_DEVICE_LOCATION,
            true
        )//This "true" default value put it same from preferences.xml.
    }

    @SuppressLint("MissingPermission") //used to third party library com.google.android.gms:play-services-location
    private fun getLastDeviceLocation(): Deferred<Location?> {
        Log.i(LOG_TAG, "TEST: getLastDeviceLocation() called...")

        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun getCustomLocationName(): String? {
        Log.i(LOG_TAG, "TEST: getCustomLocationName() called...")

        return preferences.getString(CUSTOM_LOCATION, null)
    }

    private fun hasLocationPermission(): Boolean {
        Log.i(LOG_TAG, "TEST: hasLocationPermission() called...")

        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
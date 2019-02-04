package com.freelance.android.weatherforecastmvvm.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

/**
 * Created by KyawKhine on 01/16/2019 12:05 PM.
 */

//It's relevant MainActivity.kt and below this class.
class LifecycleBoundLocationManager(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback // this method called from MainActivity.kt
) : LifecycleObserver {

    private val LOG_TAG = LifecycleBoundLocationManager::class.java.name

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val locationRequest = LocationRequest().apply {
        Log.i(LOG_TAG, "TEST: locationRequest() called...")

        interval = 5000 // 5 seconds
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("MissingPermission")
    fun startLocationUpdates(){
        Log.i(LOG_TAG, "TEST: startLocationUpdates() called...")

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeLocationUpdates(){
        Log.i(LOG_TAG, "TEST: removeLocationUpdates() called...")

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}
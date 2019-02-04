package com.freelance.android.weatherforecastmvvm.data.provider

import android.content.Context
import android.util.Log
import com.freelance.android.weatherforecastmvvm.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"//this "UNIT_SYSTEM" spelling is exactly same from android:key in preferences.xml.

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    private val LOG_TAG = UnitProviderImpl::class.java.name
    /*private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)*/

    override fun getUnitSystem(): UnitSystem {
        Log.i(LOG_TAG, "TEST: getUnitSystem() called...")

        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)//not null accepted.
    }
}
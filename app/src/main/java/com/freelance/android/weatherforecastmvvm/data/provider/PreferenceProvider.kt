package com.freelance.android.weatherforecastmvvm.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * Created by KyawKhine on 01/15/2019 6:06 PM.
 */
abstract class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}
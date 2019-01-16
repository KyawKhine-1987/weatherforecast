package com.freelance.android.weatherforecastmvvm.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.freelance.android.weatherforecastmvvm.R


class SettingsFragment : PreferenceFragmentCompat() {

    private val LOG_TAG = SettingsFragment::class.java.name

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        Log.i(LOG_TAG, "TEST: onCreatePreferences() called...")

        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onActivityCreated() called...")

        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Settings"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
    }

}
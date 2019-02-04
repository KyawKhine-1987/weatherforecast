package com.freelance.android.weatherforecastmvvm.ui.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by KyawKhine on 01/14/2019 11:35 AM.
 */


abstract class ScopedFragment : Fragment(), CoroutineScope{

    private val LOG_TAG = ScopedFragment::class.java.name
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onCreate() called...")

        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        Log.i(LOG_TAG, "TEST: onDestroy() called...")

        super.onDestroy()
        job.cancel()
    }
}
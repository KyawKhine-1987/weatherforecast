package com.freelance.android.weatherforecastmvvm.internal

import android.util.Log
import kotlinx.coroutines.*

/**
 * Created by KyawKhine on 01/14/2019 11:10 AM.
 */


fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    val LOG_TAG = "Delegates.kt"

    return lazy {
        Log.i(LOG_TAG, "TEST: lazyDeferred() called...")

        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}
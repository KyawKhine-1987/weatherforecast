package com.freelance.android.weatherforecastmvvm.internal

import kotlinx.coroutines.*

/**
 * Created by KyawKhine on 01/14/2019 11:10 AM.
 */


fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}
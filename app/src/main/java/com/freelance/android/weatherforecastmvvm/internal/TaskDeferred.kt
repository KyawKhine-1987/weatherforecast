package com.freelance.android.weatherforecastmvvm.internal

import android.util.Log
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

/**
 * Created by KyawKhine on 01/16/2019 10:49 AM.
 */


fun <T> Task<T>.asDeferred(): Deferred<T> {

    val LOG_TAG = "TaskDeferred.kt"
    val deferred = CompletableDeferred<T>()

    this.addOnSuccessListener { result ->
        deferred.complete(result)

        Log.i(LOG_TAG, "TEST: addOnSuccessListener() called...")
    }

    this.addOnFailureListener { exception ->
        deferred.completeExceptionally(exception)

        Log.i(LOG_TAG, "TEST: addOnFailureListener() called...")
    }

    return deferred
}
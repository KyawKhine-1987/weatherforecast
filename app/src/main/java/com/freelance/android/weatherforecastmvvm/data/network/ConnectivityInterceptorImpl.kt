package com.freelance.android.weatherforecastmvvm.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.freelance.android.weatherforecastmvvm.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by KyawKhine on 01/12/2019 12:19 AM.
 */


class ConnectivityInterceptorImpl(context: Context) :
    ConnectivityInterceptor {

    private val LOG_TAG = ConnectivityInterceptorImpl::class.java.name
    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i(LOG_TAG, "TEST: intercept() called...")

        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        Log.i(LOG_TAG, "TEST: isOnline() called...")

        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
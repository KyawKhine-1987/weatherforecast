package com.freelance.android.weatherforecastmvvm

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.freelance.android.weatherforecastmvvm.data.db.ForecastDatabase
import com.freelance.android.weatherforecastmvvm.data.network.*
import com.freelance.android.weatherforecastmvvm.data.provider.LocationProvider
import com.freelance.android.weatherforecastmvvm.data.provider.LocationProviderImpl
import com.freelance.android.weatherforecastmvvm.data.provider.UnitProvider
import com.freelance.android.weatherforecastmvvm.data.provider.UnitProviderImpl
import com.freelance.android.weatherforecastmvvm.data.repository.ForecastRepository
import com.freelance.android.weatherforecastmvvm.data.repository.ForecastRepositoryImpl
import com.freelance.android.weatherforecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.freelance.android.weatherforecastmvvm.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by KyawKhine on 01/13/2019 7:00 PM.
 */


class ForecastApplication : Application(), KodeinAware {

    private val LOG_TAG = ForecastApplication::class.java.name

    override val kodein = Kodein.lazy {
        Log.i(LOG_TAG, "TEST: kodein () called...")

        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton {
            ForecastRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        Log.i(LOG_TAG, "TEST: onCreate() called...")

        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}
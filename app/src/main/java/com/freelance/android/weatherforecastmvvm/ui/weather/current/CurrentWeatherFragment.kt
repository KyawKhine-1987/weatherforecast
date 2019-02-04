package com.freelance.android.weatherforecastmvvm.ui.weather.current

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freelance.android.weatherforecastmvvm.R
import com.freelance.android.weatherforecastmvvm.internal.glide.GlideApp
import com.freelance.android.weatherforecastmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    private val LOG_TAG = CurrentWeatherFragment::class.java.name
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel //this code is created new Object for CurrentWeatherViewModel.kt.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(LOG_TAG, "TEST: onCreateView() called...")

        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onActivityCreated() called...")

        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()
        // TODO: Use the ViewModel
        /*val apiService = ApixuWeatherApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)

        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
            tvCurrent.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiService.getCurrentWeather("Yangon").await()
            Log.d(LOG_TAG, currentWeatherResponse.toString())
            //tvCurrent.text = currentWeatherResponse.currentWeatherEntry.toString()
            weatherNetworkDataSource.fetchCurrentWeather("Yangon", "en")
        }*/
    }

    /*private fun bindUI() = GlobalScope.launch {*/
    private fun bindUI() = launch {
        Log.i(LOG_TAG, "TEST: bindUI() called...")
        // This CoroutineScope method from ScopedFragment.kt.
        val currentWeather = viewModel.weather.await()

        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@CurrentWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            currentGroup.visibility = View.GONE
            //updateLocation("Yangon")
            updateDateToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemprature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(civConditionIcon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        Log.i(LOG_TAG, "TEST: chooseLocalizedUnitAbbreviation() called...")

        /*return if (viewModel.isMetric) metric else imperial*/ //got Error from Part-12.
        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateLocation(location: String) {
        Log.i(LOG_TAG, "TEST: updateLocation() called...")

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        Log.i(LOG_TAG, "TEST: updateDateToToday() called...")

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        Log.i(LOG_TAG, "TEST: updateTemperatures() called...")

        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        tvTemperature.text = "$temperature $unitAbbreviation"
        tvfeels_like_temperature.text = "Feels like: $feelsLike $unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        Log.i(LOG_TAG, "TEST: updateCondition() called...")

        tvCondition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        Log.i(LOG_TAG, "TEST: updatePrecipitation() called...")

        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in") //millimeter and inches
        tvPrecipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        Log.i(LOG_TAG, "TEST: updateWind() called...")

        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph") //millimeter and inches
        tvWind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        Log.i(LOG_TAG, "TEST: updateVisibility() called...")

        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.") //millimeter and inches
        tvVisibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }
}

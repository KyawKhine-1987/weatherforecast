package com.freelance.android.weatherforecastmvvm.ui.weather.future.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freelance.android.weatherforecastmvvm.R
import com.freelance.android.weatherforecastmvvm.data.db.LocalDateConverter
import com.freelance.android.weatherforecastmvvm.internal.DateNotFoundException
import com.freelance.android.weatherforecastmvvm.internal.glide.GlideApp
import com.freelance.android.weatherforecastmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    private val LOG_TAG = FutureDetailWeatherFragment::class.java.name

    override val kodein by closestKodein()
    private lateinit var viewModel: FutureDetailWeatherViewModel
    private val viewModelFactoryInstanceFactory
            : ((LocalDate) -> FutureDetailWeatherViewModelFactory) by factory()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(LOG_TAG, "TEST: onCreateView() called...")

        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onActivityCreated() called...")

        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { FutureDetailWeatherFragmentArgs.fromBundle(it) }
        val date = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundException()

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(date))
            .get(FutureDetailWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        Log.i(LOG_TAG, "TEST: bindUI() called...")

        val futureWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@FutureDetailWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        futureWeather.observe(this@FutureDetailWeatherFragment, Observer { weatherEntry ->
            if (weatherEntry == null) return@Observer
            updateDate(weatherEntry.date)
            updateTemperatures(
                weatherEntry.avgTemperature,
                weatherEntry.minTemperature,
                weatherEntry.maxTemperature)
            updateCondition(weatherEntry.conditionText)
            updatePrecipitation(weatherEntry.totalPrecipitation)
            updateWindSpeed(weatherEntry.maxWindSpeed)
            updateVisibility(weatherEntry.avgVisibilityDistance)
            updateUV(weatherEntry.uv)

            GlideApp.with(this@FutureDetailWeatherFragment)
                .load("http:" + weatherEntry.conditionIconUrl)
                .into(fivConditionIcon)
        })
    }


    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String{
        Log.i(LOG_TAG, "TEST: chooseLocalizedUnitAbbreviation() called...")

        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateLocation(location: String) {
        Log.i(LOG_TAG, "TEST: updateLocation() called...")

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDate(date: LocalDate) {
        Log.i(LOG_TAG, "TEST: updateLocation() called...")

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTemperatures(temperature: Double, min: Double, max: Double) {
        Log.i(LOG_TAG, "TEST: updateLocation() called...")

        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        tvTemperature.text = "$temperature$unitAbbreviation"
        tvMin_Max_Temperature.text = "Min: $min$unitAbbreviation, Max: $max$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        tvCondition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        tvPrecipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWindSpeed(windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        tvWind.text = "Wind speed: $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        tvVisibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

    private fun updateUV(uv: Double) {
        tvUV.text = "UV: $uv"
    }
}

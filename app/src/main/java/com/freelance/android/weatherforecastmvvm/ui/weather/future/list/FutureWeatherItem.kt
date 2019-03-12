package com.freelance.android.weatherforecastmvvm.ui.weather.future.list

import android.util.Log
import com.freelance.android.weatherforecastmvvm.R
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.freelance.android.weatherforecastmvvm.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
/**
 * Created by KyawKhine on 01/21/2019 4:12 PM.
 */


class FutureWeatherItem(
    val weatherEntry : UnitSpecificSimpleFutureWeatherEntry
) : Item() {

    private val LOG_TAG = FutureWeatherItem::class.java.name

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Log.i(LOG_TAG, "TEST: bind() called...")

        viewHolder.apply {
            fltvCondition.text = weatherEntry.conditionText
            updateDate()
            updateTemperature()
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun ViewHolder.updateDate() {
        Log.i(LOG_TAG, "TEST: ViewHolder.updateDate() called...")

        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        fltvDate.text = weatherEntry.date.format(dtFormatter)
    }

    private fun ViewHolder.updateTemperature(){
        Log.i(LOG_TAG, "TEST: ViewHolder.updateTemperature() called...")

        val unitAbbreviation = if (weatherEntry is MetricSimpleFutureWeatherEntry) "°C" else "°F"
        fltvTemperature.text = "${weatherEntry.avgTemperature}$unitAbbreviation"
    }

    private fun ViewHolder.updateConditionImage(){
        Log.i(LOG_TAG, "TEST: ViewHolder.updateConditionImage() called...")

        GlideApp.with(this.containerView)
            .load("https:" + weatherEntry.conditionIconUrl)
            .into(flivConditionIcon)
    }


}
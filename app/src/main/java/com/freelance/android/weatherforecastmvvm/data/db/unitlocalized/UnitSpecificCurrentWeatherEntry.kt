package com.freelance.android.weatherforecastmvvm.data.db.unitlocalized

/**
 * Created by KyawKhine on 01/11/2019 3:45 PM.
 */


interface UnitSpecificCurrentWeatherEntry {
    val temperature : Double
    val conditionText : String
    val conditionIconUrl : String
    val windSpeed : Double
    val windDirection : String
    val precipitationVolume : Double
    val feelsLikeTemprature : Double
    val visibilityDistance : Double
}
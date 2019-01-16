package com.freelance.android.weatherforecastmvvm.data.provider

import com.freelance.android.weatherforecastmvvm.internal.UnitSystem

/**
 * Created by KyawKhine on 01/15/2019 11:52 AM.
 */


interface UnitProvider {

    fun getUnitSystem() : UnitSystem
}
package com.freelance.android.weatherforecastmvvm.internal

import java.io.IOException

/**
 * Created by KyawKhine on 01/12/2019 9:01 AM.
 */


class NoConnectivityException : IOException()
class LocationPermissionNotGrantedException: Exception()
class DateNotFoundException: Exception()
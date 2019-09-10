package com.reyesmagos.squarefinder.map.providers

import android.content.Context
import android.location.Location
import kotlinx.coroutines.Deferred

interface LocationProvider {

    fun getLocationAsync(context: Context): Deferred<Location>

    fun disableLocationListener()
}

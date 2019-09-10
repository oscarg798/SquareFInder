package com.reyesmagos.squarefinder.map.providers

import android.content.Context
import android.location.Location
import io.reactivex.Observable
import kotlinx.coroutines.Deferred

interface LocationProvider {

    fun getLocation(context: Context): Observable<Location>

    fun disableLocationListener()
}

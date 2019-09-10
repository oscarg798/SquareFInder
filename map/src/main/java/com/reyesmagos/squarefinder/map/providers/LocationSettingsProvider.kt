package com.reyesmagos.squarefinder.map.providers

import android.app.Activity
import kotlinx.coroutines.Deferred

interface LocationSettingsProvider {

    fun checkLocationUpdatesAsync(
        activity: Activity
    ): Deferred<Unit>
}

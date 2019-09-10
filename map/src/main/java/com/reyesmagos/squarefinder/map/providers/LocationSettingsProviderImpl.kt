package com.reyesmagos.squarefinder.map.providers

import android.app.Activity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class LocationSettingsProviderImpl : BaseLocationProvider(),
    LocationSettingsProvider {

    override fun checkLocationUpdatesAsync(activity: Activity): Deferred<Unit> {
        val deferred = CompletableDeferred<Unit>()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(getLocationRequest())

        val client = LocationServices.getSettingsClient(activity)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener(
            activity
        ) { deferred.complete(Unit) }

        task.addOnFailureListener(activity) { e ->
            deferred.completeExceptionally(e)
        }

        return deferred

    }
}

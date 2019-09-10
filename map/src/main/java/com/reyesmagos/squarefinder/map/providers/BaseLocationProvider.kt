package com.reyesmagos.squarefinder.map.providers

import com.google.android.gms.location.LocationRequest

abstract class BaseLocationProvider {

    fun getLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create()
        locationRequest.interval =
            LOCATION_REQUEST_INTERVAL
        locationRequest.fastestInterval =
            LOCATION_REQUEST_INTERVAL
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return locationRequest
    }

    companion object {
        private const val LOCATION_REQUEST_INTERVAL = 5000L
    }
}

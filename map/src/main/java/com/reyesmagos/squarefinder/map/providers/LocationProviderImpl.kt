package com.reyesmagos.squarefinder.map.providers

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.reyesmagos.squarefinder.map.exceptions.LocationNotFoundException
import com.reyesmagos.squarefinder.map.exceptions.LocationPermissionsNotGrantedException
import io.reactivex.Observable

class LocationProviderImpl : BaseLocationProvider(),
    LocationProvider {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private var locationCallback: LocationCallback? = null

    override fun getLocation(context: Context): Observable<Location> {

        return Observable.create { emitter ->

            disableLocationListener()

            if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                emitter.onError(LocationPermissionsNotGrantedException())
            }

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            locationCallback = object : LocationCallback() {

                override fun onLocationResult(result: LocationResult?) {
                    super.onLocationResult(result)

                    if (result?.lastLocation != null) {
                        emitter.onNext(result.lastLocation)

                    } else {
                        emitter.onError(LocationNotFoundException())
                    }
                }

                override fun onLocationAvailability(p0: LocationAvailability?) {
                    super.onLocationAvailability(p0)
                    p0?.isLocationAvailable
                }
            }

            fusedLocationProviderClient?.requestLocationUpdates(
                getLocationRequest(),
                locationCallback!!,
                null
            )
        }
    }

    override fun disableLocationListener() {
        val locationCallback = locationCallback ?: return
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
        this.locationCallback = null
    }


}

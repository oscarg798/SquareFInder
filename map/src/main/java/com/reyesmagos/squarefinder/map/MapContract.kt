package com.reyesmagos.squarefinder.map

import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.OnMapReadyCallback
import com.reyesmagos.squarefinder.core.contracts.BasePresenter
import com.reyesmagos.squarefinder.core.contracts.BaseView
import kotlinx.coroutines.Deferred

interface MapContract {

    interface View : BaseView, OnMapReadyCallback {

        fun showUserLocation(location: Location)

        fun checkLocationPermissions()

        fun showError(error: String)

        fun showError(errorStringId: Int)

        fun turnOnLocationManagerAsync(): Deferred<Unit>

        fun getUserLocationAsync(): Deferred<Location>

        fun startResolutionRequest(resolvableApiException: ResolvableApiException)
    }

    interface Presenter : BasePresenter<View> {

        fun onViewReady()

        fun onPermissionsGranted()

        fun onLocationManagerEnabled()

    }
}

package com.reyesmagos.squarefinder.map

import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.reyesmagos.squarefinder.core.contracts.BasePresenter
import com.reyesmagos.squarefinder.core.contracts.BaseView
import com.reyesmagos.squarefinder.core.models.ViewVenue
import io.reactivex.Observable
import kotlinx.coroutines.Deferred

interface MapContract {

    interface View : BaseView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

        fun showUserLocation(location: Location)

        fun checkLocationPermissions()

        fun showError(error: String)

        fun showError(errorStringId: Int)

        fun turnOnLocationManagerAsync(): Deferred<Unit>

        fun getUserLocation(): Observable<Location>

        fun startResolutionRequest(resolvableApiException: ResolvableApiException)

        fun showCoffeeShops(shops: List<ViewVenue>)

        fun openShowDetail(viewVenue: ViewVenue)
    }

    interface Presenter : BasePresenter<View> {

        fun onViewReady()

        fun onPermissionsGranted()

        fun onLocationManagerEnabled()

        fun onCoffeeShopClick(viewVenue: ViewVenue)

    }
}

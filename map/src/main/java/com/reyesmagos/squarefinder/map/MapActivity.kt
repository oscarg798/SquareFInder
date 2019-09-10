package com.reyesmagos.squarefinder.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.reyesmagos.squarefinder.core.CoreComponentProvider
import com.reyesmagos.squarefinder.core.REQUEST_CODE_LOCATION_PERMISSIONS
import com.reyesmagos.squarefinder.core.REQUEST_ENABLE_LOCATION_MANAGER
import com.reyesmagos.squarefinder.core.models.ViewVenue
import com.reyesmagos.squarefinder.map.di.DaggerMapComponent
import com.reyesmagos.squarefinder.map.di.MapModule
import com.reyesmagos.squarefinder.map.providers.LocationProvider
import com.reyesmagos.squarefinder.map.providers.LocationSettingsProvider
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class MapActivity : AppCompatActivity(), MapContract.View {

    private var googleMap: GoogleMap? = null

    @Inject
    lateinit var locationSettingsProvider: LocationSettingsProvider

    @Inject
    lateinit var locationProvider: LocationProvider

    @Inject
    lateinit var presenter: MapContract.Presenter

    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        DaggerMapComponent.builder()
            .coreComponent((application as CoreComponentProvider).getCoreComponent())
            .mapModule(MapModule())
            .build()
            .inject(this)

        presenter.bind(this)
        initMap()

    }

    private fun initMap() {
        (supportFragmentManager?.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync(
            this
        )
    }

    override fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSIONS
            )

        } else {
            presenter.onPermissionsGranted()
        }
    }

    override fun showCoffeeShops(shops: List<ViewVenue>) {
        shops.forEach {
            googleMap?.addMarker(
                MarkerOptions().position(LatLng(it.latitude, it.longitude))
                    .title(it.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            )?.apply {
                tag = it
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS) {
            if ((grantResults.isNotEmpty() && grantResults.none {
                    it == PackageManager.PERMISSION_DENIED
                })) {
                presenter.onPermissionsGranted()
            } else {
                showError(getString(R.string.location_permission_error))
            }
        }
    }

    override fun showError(error: String) {
        if (errorSnackbar?.isShown == true) {
            errorSnackbar?.dismiss()
        }
        val view = clMain ?: return

        errorSnackbar = Snackbar.make(view, error, Snackbar.LENGTH_INDEFINITE).setAction(
            getString(R.string.dismiss)
        ) {
            errorSnackbar?.dismiss()
        }

        errorSnackbar?.show()
    }

    override fun showError(errorStringId: Int) {
        showError(getString(errorStringId))
    }

    override fun showUserLocation(location: Location) {
        val position = LatLng(
            location.latitude,
            location.longitude
        )

        googleMap?.clear()

        googleMap?.addMarker(
            MarkerOptions().position(position)
        )

        googleMap?.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder().target(LatLng(position.latitude, position.longitude))
                    .zoom(DEFAULT_CAMERA_ZOOM)
                    .build()
            )
        )

        googleMap?.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val venue = marker?.tag as? ViewVenue ?: return false
        marker.showInfoWindow()
        presenter.onCoffeeShopClick(venue)

        return true
    }

    override fun getUserLocation(): Observable<Location> {
        return locationProvider.getLocation(this)
    }

    override fun turnOnLocationManagerAsync(): Deferred<Unit> {
        return locationSettingsProvider.checkLocationUpdatesAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap ?: return
        googleMap.apply {
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMapToolbarEnabled = false
        }

        presenter.onViewReady()
    }

    override fun onStop() {
        super.onStop()
        presenter.unBind()
        locationProvider.disableLocationListener()
    }

    override fun startResolutionRequest(resolvableApiException: ResolvableApiException) {
        resolvableApiException.startResolutionForResult(this, REQUEST_ENABLE_LOCATION_MANAGER)
    }

    companion object {

        private const val DEFAULT_CAMERA_ZOOM = 12f
    }

}

package com.reyesmagos.squarefinder.map

import android.location.Location
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import com.reyesmagos.squarefinder.core.Interactor
import com.reyesmagos.squarefinder.core.models.Venue
import com.reyesmagos.squarefinder.core.models.ViewVenue
import com.reyesmagos.squarefinder.map.models.GetVenueParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class MapActivityPresenter(
    private val getVenueInteractor: Interactor<List<Venue>, GetVenueParams>,
    override val coroutinesContextProvider: CoroutineContextProvider
) :
    MapContract.Presenter {

    override val parentJob: Job = Job()

    override var view: MapContract.View? = null

    private val compositeDisposable = CompositeDisposable()

    private var currentLocation: Location? = null

    override fun onViewReady() {
        view?.checkLocationPermissions()
    }

    override fun onPermissionsGranted() {
        val view = view ?: return

        launchJobOnMainDispatchers {
            try {
                withContext(coroutinesContextProvider.backgroundContext) {
                    view.turnOnLocationManagerAsync().await()
                }
                onLocationManagerEnabled()
            } catch (t: Throwable) {
                handleException(t)
            }
        }
    }

    override fun onLocationManagerEnabled() {
        val view = view ?: return

        launchJobOnMainDispatchers {
            try {
                val locationObservable = withContext(coroutinesContextProvider.mainContext) {
                    view.getUserLocation()
                }

                compositeDisposable.add(
                    locationObservable.subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (shouldUpdateLocation(it)) {
                                this@MapActivityPresenter.view?.showUserLocation(it)
                                getNearCoffeeShops(it)
                            }
                        }, {
                            handleException(it)
                        })
                )
            } catch (t: Throwable) {
                handleException(t)
            }
        }
    }

    private fun shouldUpdateLocation(newLocation: Location): Boolean {
        if (currentLocation == null) {
            currentLocation = newLocation
            return true
        }


        val shouldUpdate = currentLocation!!.distanceTo(newLocation) > MINIMUM_DISTANCE_TO_UPDATE_IN_METERS

        if (shouldUpdate) {
            currentLocation = newLocation
        }

        return shouldUpdate
    }

    override fun onCoffeeShopClick(viewVenue: ViewVenue) {
       view?.openShowDetail(viewVenue)
    }

    override fun unBind() {
        super.unBind()
        compositeDisposable.clear()
    }

    private fun getNearCoffeeShops(location: Location) {
        launchJobOnMainDispatchers {
            try {
                val venues = withContext(coroutinesContextProvider.backgroundContext) {
                    val result =
                        getVenueInteractor(GetVenueParams(location.latitude, location.longitude))
                    result.map {
                        ViewVenue(
                            it.name,
                            getFormattedAddressAsString(it.location.formattedAddress),
                            it.location.lat,
                            it.location.lng
                        )
                    }
                }

                view?.showCoffeeShops(venues)
            } catch (t: Throwable) {
                handleException(t)
            }
        }
    }

    private fun getFormattedAddressAsString(formattedAddress: List<String>): String {
        return formattedAddress.joinToString(",")
    }

    private fun handleException(t: Throwable) {

        when {
            t is ResolvableApiException -> {
                onResolutionResult(t)
            }
            t.message != null -> view?.showError(t.message!!)
            else -> view?.showError(R.string.default_error)
        }
    }

    private fun onResolutionResult(resolvableApiException: ResolvableApiException) {
        view?.startResolutionRequest(resolvableApiException)
    }

    companion object{
        private const val MINIMUM_DISTANCE_TO_UPDATE_IN_METERS= 10
    }
}

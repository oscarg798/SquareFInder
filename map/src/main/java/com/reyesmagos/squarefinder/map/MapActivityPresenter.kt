package com.reyesmagos.squarefinder.map

import com.google.android.gms.common.api.ResolvableApiException
import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class MapActivityPresenter(override val coroutinesContextProvider: CoroutineContextProvider) :
    MapContract.Presenter {

    override val parentJob: Job = Job()

    override var view: MapContract.View? = null

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
                val location = withContext(coroutinesContextProvider.mainContext) {
                    view.getUserLocationAsync().await()
                }

                view.showUserLocation(location)
            } catch (t: Throwable) {
                handleException(t)
            }
        }
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
}

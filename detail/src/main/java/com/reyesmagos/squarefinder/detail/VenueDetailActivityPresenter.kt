package com.reyesmagos.squarefinder.detail

import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import com.reyesmagos.squarefinder.core.models.ViewVenue
import kotlinx.coroutines.Job

class VenueDetailActivityPresenter(override val coroutinesContextProvider: CoroutineContextProvider) :
    VenueDetailContract.Presenter {

    override val parentJob: Job = Job()
    override var view: VenueDetailContract.View? = null

    override fun onViewReady(viewVenue: ViewVenue) {
        view?.showCoffeeName(viewVenue.name)
        view?.showAddress(viewVenue.address)
    }


}

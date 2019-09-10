package com.reyesmagos.squarefinder.map.interactors

import com.reyesmagos.squarefinder.core.Interactor
import com.reyesmagos.squarefinder.core.models.Venue
import com.reyesmagos.squarefinder.map.models.GetVenueParams
import com.reyesmagos.squarefinder.map.repository.VenueRepository

class GetVenueInteractor(private val venueRepository: VenueRepository) :
    Interactor<List<Venue>, GetVenueParams> {

    override suspend fun invoke(params: GetVenueParams): List<Venue> {
        return venueRepository.getVenues(params.lat, params.lng)
    }
}

package com.reyesmagos.squarefinder.map.repository

import com.reyesmagos.squarefinder.core.models.Venue
import com.reyesmagos.squarefinder.core.models.VenueLocation
import com.reyesmagos.squarefinder.map.service.GetVenueService

class VenueRepositoryImpl(private val venueService: GetVenueService) : VenueRepository {

    override suspend fun getVenues(latitude: Double, longitude: Double): List<Venue> {
        val venues = venueService.getVenues("$latitude,$longitude").response.venues

        return venues.map {
            Venue(
                it.name,
                VenueLocation(
                    it.location.address,
                    it.location.crossStreet,
                    it.location.lat,
                    it.location.lng,
                    it.location.city,
                    it.location.state,
                    it.location.country,
                    it.location.postalCode,
                    it.location.formattedAddress
                )
            )
        }
    }
}

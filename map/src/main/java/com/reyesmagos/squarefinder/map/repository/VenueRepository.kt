package com.reyesmagos.squarefinder.map.repository

import com.reyesmagos.squarefinder.core.models.Venue

interface VenueRepository {

    suspend fun getVenues(latitude: Double, longitude: Double): List<Venue>
}

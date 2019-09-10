package com.reyesmagos.squarefinder.core.models

data class VenueLocation(
    val address: String,
    val crossStreet: String,
    val lat: Double,
    val lng: Double,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val formattedAddress: List<String>
)

data class Venue(val name: String,
                 val location: VenueLocation)

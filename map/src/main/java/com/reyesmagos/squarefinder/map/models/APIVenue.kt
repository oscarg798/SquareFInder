package com.reyesmagos.squarefinder.map.models

data class APIVenueLocation(
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

data class APIVenue(val name: String,
                 val location: APIVenueLocation)

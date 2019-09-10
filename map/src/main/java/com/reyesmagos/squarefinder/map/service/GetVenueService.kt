package com.reyesmagos.squarefinder.map.service

import com.reyesmagos.squarefinder.map.models.APIVenue
import com.reyesmagos.squarefinder.map.models.GetVenuesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GetVenueService {

    @GET("venue/search")
    suspend fun getVenues(
        @Query("ll") latLng: String,
        @Query("query") query: String = "coffee",
        @Query("v") v: Int = 20180901
    ): GetVenuesResponse
}

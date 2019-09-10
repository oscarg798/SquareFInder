package com.reyesmagos.squarefinder.map.service

import com.reyesmagos.squarefinder.map.models.GetVenuesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GetVenueService {

    @GET("venues/search")
    suspend fun getVenues(
        @Query("ll") latLng: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("query") query: String = "coffee",
        @Query("v") v: Int = 20180901

    ): GetVenuesResponse
}

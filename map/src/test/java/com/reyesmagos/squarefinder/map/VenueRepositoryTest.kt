package com.reyesmagos.squarefinder.map

import com.reyesmagos.squarefinder.map.models.APIVenue
import com.reyesmagos.squarefinder.map.models.APIVenueLocation
import com.reyesmagos.squarefinder.map.models.GetVenuesResponse
import com.reyesmagos.squarefinder.map.models.VenuesResponse
import com.reyesmagos.squarefinder.map.repository.VenueRepository
import com.reyesmagos.squarefinder.map.repository.VenueRepositoryImpl
import com.reyesmagos.squarefinder.map.service.GetVenueService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class VenueRepositoryTest {


    @MockK
    lateinit var getVenueService: GetVenueService

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        coEvery {
            getVenueService.getVenues("1.1,2.2")
        }.answers {
            GetVenuesResponse(
                VenuesResponse(
                    listOf(
                        APIVenue(
                            "1", APIVenueLocation(
                                "2", "3", 4.4, 5.5, "6", "7", "8", "9",
                                listOf("10")
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `should get venues`() {
        //Given
        val repository: VenueRepository = VenueRepositoryImpl(getVenueService)

        //When
        val venues = runBlocking {
            repository.getVenues(1.1, 2.2)
        }

        //Then
        venues.size shouldEqual 1
        venues[0].name shouldEqual "1"
        venues[0].location.address shouldEqual "2"
        venues[0].location.crossStreet shouldEqual "3"
        venues[0].location.lat shouldEqual 4.4
        venues[0].location.lng shouldEqual 5.5
        venues[0].location.city shouldEqual "6"
        venues[0].location.state shouldEqual "7"
        venues[0].location.country shouldEqual "8"
        venues[0].location.postalCode shouldEqual "9"
        venues[0].location.formattedAddress.size shouldEqual 1
        venues[0].location.formattedAddress[0] shouldEqual "10"
    }
}

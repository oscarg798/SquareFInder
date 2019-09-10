package com.reyesmagos.squarefinder.map

import com.reyesmagos.squarefinder.core.Interactor
import com.reyesmagos.squarefinder.core.models.Venue
import com.reyesmagos.squarefinder.core.models.VenueLocation
import com.reyesmagos.squarefinder.map.interactors.GetVenueInteractor
import com.reyesmagos.squarefinder.map.models.GetVenueParams
import com.reyesmagos.squarefinder.map.repository.VenueRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class GetVenueInteractorTest {

    @MockK
    lateinit var venueRepository: VenueRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery {
            venueRepository.getVenues(1.1, 2.2)
        }.answers {
            listOf(
                Venue(
                    "1", VenueLocation(
                        "2", "3", 4.4, 5.5, "6", "7", "8", "9",
                        listOf("10")
                    )
                )
            )
        }
    }

    @Test
    fun `should get venues`() {
        //Given
        val interactor: Interactor<List<Venue>, GetVenueParams> = GetVenueInteractor(venueRepository)

        //When
        val venues = runBlocking {
            interactor(GetVenueParams(1.1, 2.2))
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

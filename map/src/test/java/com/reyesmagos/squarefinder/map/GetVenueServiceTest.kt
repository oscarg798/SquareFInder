package com.reyesmagos.squarefinder.map

import com.reyesmagos.squarefinder.core.di.NetworkModule
import com.reyesmagos.squarefinder.map.service.GetVenueService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.Before
import org.junit.Test

class GetVenueServiceTest : MockServerTest() {

    private val networkModule = NetworkModule()

    @Before
    override fun setup() {
        super.setup()
        mockServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody("{\n" +
                        "    \"meta\": {\n" +
                        "        \"code\": 200,\n" +
                        "        \"requestId\": \"5d77c506787dba003887415d\"\n" +
                        "    },\n" +
                        "    \"response\": {\n" +
                        "        \"venues\": [\n" +
                        "            {\n" +
                        "                \"id\": \"512f8233e4b04458d4385195\",\n" +
                        "                \"name\": \"Stumptown Coffee Roasters\",\n" +
                        "                \"location\": {\n" +
                        "                    \"address\": \"30 W 8th St\",\n" +
                        "                    \"crossStreet\": \"at MacDougal St\",\n" +
                        "                    \"lat\": 40.732797,\n" +
                        "                    \"lng\": -73.997971,\n" +
                        "                    \"labeledLatLngs\": [\n" +
                        "                        {\n" +
                        "                            \"label\": \"display\",\n" +
                        "                            \"lat\": 40.732797,\n" +
                        "                            \"lng\": -73.997971\n" +
                        "                        }\n" +
                        "                    ],\n" +
                        "                    \"distance\": 999,\n" +
                        "                    \"postalCode\": \"10011\",\n" +
                        "                    \"cc\": \"US\",\n" +
                        "                    \"city\": \"New York\",\n" +
                        "                    \"state\": \"NY\",\n" +
                        "                    \"country\": \"United States\",\n" +
                        "                    \"formattedAddress\": [\n" +
                        "                        \"30 W 8th St (at MacDougal St)\",\n" +
                        "                        \"New York, NY 10011\",\n" +
                        "                        \"United States\"\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                \"categories\": [\n" +
                        "                    {\n" +
                        "                        \"id\": \"4bf58dd8d48988d1e0931735\",\n" +
                        "                        \"name\": \"Coffee Shop\",\n" +
                        "                        \"pluralName\": \"Coffee Shops\",\n" +
                        "                        \"shortName\": \"Coffee Shop\",\n" +
                        "                        \"icon\": {\n" +
                        "                            \"prefix\": \"https://ss3.4sqi.net/img/categories_v2/food/coffeeshop_\",\n" +
                        "                            \"suffix\": \".png\"\n" +
                        "                        },\n" +
                        "                        \"primary\": true\n" +
                        "                    }\n" +
                        "                ],\n" +
                        "                \"referralId\": \"v-1568130311\",\n" +
                        "                \"hasPerk\": false\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\": \"5abfe6bbc0af5705a1bcacdb\",\n" +
                        "                \"name\": \"COFFEE & CREAM by Oddfellows\",\n" +
                        "                \"location\": {\n" +
                        "                    \"address\": \"55 E Houston St\",\n" +
                        "                    \"crossStreet\": \"at Mott St\",\n" +
                        "                    \"lat\": 40.724707325648026,\n" +
                        "                    \"lng\": -73.99428101796822,\n" +
                        "                    \"labeledLatLngs\": [\n" +
                        "                        {\n" +
                        "                            \"label\": \"display\",\n" +
                        "                            \"lat\": 40.724707325648026,\n" +
                        "                            \"lng\": -73.99428101796822\n" +
                        "                        }\n" +
                        "                    ],\n" +
                        "                    \"distance\": 635,\n" +
                        "                    \"postalCode\": \"10012\",\n" +
                        "                    \"cc\": \"US\",\n" +
                        "                    \"city\": \"New York\",\n" +
                        "                    \"state\": \"NY\",\n" +
                        "                    \"country\": \"United States\",\n" +
                        "                    \"formattedAddress\": [\n" +
                        "                        \"55 E Houston St (at Mott St)\",\n" +
                        "                        \"New York, NY 10012\",\n" +
                        "                        \"United States\"\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                \"categories\": [\n" +
                        "                    {\n" +
                        "                        \"id\": \"4bf58dd8d48988d1c9941735\",\n" +
                        "                        \"name\": \"Ice Cream Shop\",\n" +
                        "                        \"pluralName\": \"Ice Cream Shops\",\n" +
                        "                        \"shortName\": \"Ice Cream\",\n" +
                        "                        \"icon\": {\n" +
                        "                            \"prefix\": \"https://ss3.4sqi.net/img/categories_v2/food/icecream_\",\n" +
                        "                            \"suffix\": \".png\"\n" +
                        "                        },\n" +
                        "                        \"primary\": true\n" +
                        "                    }\n" +
                        "                ],\n" +
                        "                \"referralId\": \"v-1568130311\",\n" +
                        "                \"hasPerk\": false\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}"
                )
        )
    }

    @Test
    fun `should get venues`() {
        //given
        val service = networkModule.provideRetrofit(
            mockServer.url(" ").toString(),
            networkModule.provideGsonConverter(),
            networkModule.provideHttpClient(networkModule.provideLogginInterceptor())
        ).create(GetVenueService::class.java)

        //when
        val result = runBlocking {
            service.getVenues("40.7243,-74.0018")
        }

        result shouldNotEqual null
        result.response.venues.size shouldEqual 2
        result.response.venues[0].name shouldEqual "Stumptown Coffee Roasters"
        result.response.venues[0].location.address shouldEqual "30 W 8th St"
        result.response.venues[0].location.city shouldEqual "New York"
        result.response.venues[0].location.country shouldEqual "United States"
        result.response.venues[0].location.crossStreet shouldEqual "at MacDougal St"
        result.response.venues[0].location.lat shouldEqual 40.732797
        result.response.venues[0].location.lng shouldEqual -73.997971
        result.response.venues[0].location.postalCode shouldEqual "10011"
        result.response.venues[0].location.state shouldEqual "NY"
        result.response.venues[0].location.formattedAddress[0] shouldEqual "30 W 8th St (at MacDougal St)"
        result.response.venues[0].location.formattedAddress[1] shouldEqual "New York, NY 10011"
        result.response.venues[0].location.formattedAddress[2] shouldEqual "United States"

        result.response.venues[1].name shouldEqual "COFFEE & CREAM by Oddfellows"
        result.response.venues[1].location.address shouldEqual "55 E Houston St"
        result.response.venues[1].location.city shouldEqual "New York"
        result.response.venues[1].location.country shouldEqual "United States"
        result.response.venues[1].location.crossStreet shouldEqual "at Mott St"
        result.response.venues[1].location.lat shouldEqual 40.724707325648026
        result.response.venues[1].location.lng shouldEqual -73.99428101796822
        result.response.venues[1].location.postalCode shouldEqual "10012"
        result.response.venues[1].location.state shouldEqual "NY"
        result.response.venues[1].location.formattedAddress[0] shouldEqual "55 E Houston St (at Mott St)"
        result.response.venues[1].location.formattedAddress[1] shouldEqual "New York, NY 10012"
        result.response.venues[1].location.formattedAddress[2] shouldEqual "United States"
    }

}

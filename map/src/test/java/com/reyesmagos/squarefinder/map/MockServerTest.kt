package com.reyesmagos.squarefinder.map

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class MockServerTest {

    lateinit var mockServer: MockWebServer

    @Before
    open fun setup(){
        mockServer = MockWebServer()
        mockServer.start()
    }

    @After
    fun tearDown(){
        mockServer.shutdown()
    }

}

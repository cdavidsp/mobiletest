package com.cesarsosa.mobiletest.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cesarsosa.mobiletest.util.toCharacter
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MarvelServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MarvelService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestCharacters() {
        runBlocking {
            enqueueResponse("characters.json")
            val resultResponse = service.getCharacters().body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/characters"))
        }
    }

    @Test
    fun getCharactersResponse() {
        runBlocking {
            enqueueResponse("characters.json")
            val resultResponse = service.getCharacters().body()
            val characters = resultResponse!!.data!!.results.map { it.toCharacter() }

            assertNotNull(resultResponse.data)
            assertThat(resultResponse.code,`is`(200))
            assertThat(resultResponse.status,`is`("Ok"))
            assertThat(resultResponse.data!!.count, `is`(20))
            assertThat(characters.size, `is`(20))
        }
    }
    @Test
    fun getCharacterByIdResponse() {
        runBlocking {
            val id="1009149";
            enqueueResponse("character_$id.json")
            val resultResponse = service.getCharacter(id).body()
            val characters = resultResponse!!.data!!.results.map { it.toCharacter() }

            assertNotNull(resultResponse.data)
            assertThat(resultResponse.code,`is`(200))
            assertThat(resultResponse.status,`is`("Ok"))
            assertThat(resultResponse.data!!.count, `is`(1))
            assertThat(characters.size, `is`(1))
        }
    }

    @Test
    fun getCharactersPagination() {
        runBlocking {
            enqueueResponse("characters.json")
            val resultResponse = service.getCharacters().body()

            assertThat(resultResponse!!.data!!.limit, `is`(20))
            assertThat(resultResponse!!.data!!.total, `is`(1493))
            assertThat(resultResponse!!.data!!.offset, `is`(0))
        }
    }


    @Test
    fun getCharacterFirstItem() {
        runBlocking {
            enqueueResponse("characters.json")
            val resultResponse = service.getCharacters().body()
            val characters = resultResponse!!.data!!.results.map { it.toCharacter() }

            val character = characters[0]
            assertThat(character.id, `is`("1011334"))
            assertThat(character.name, `is`("3-D Man"))
            assertThat(character.description, `is`(""))
        }
    }
    @Test
    fun getCharacterItem() {
        runBlocking {
            val id="1009149";
            enqueueResponse("character_$id.json")
            val resultResponse = service.getCharacter(id).body()
            val characters = resultResponse!!.data!!.results.map { it.toCharacter() }

            val character = characters[0]
            assertThat(character.id, `is`("1009149"))
            assertThat(character.name, `is`("Abyss"))
            assertThat(character.description, `is`(""))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
                .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
                source.readString(Charsets.UTF_8))
        )
    }
}

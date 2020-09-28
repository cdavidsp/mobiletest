package com.cesarsosa.mobiletest.api

import com.cesarsosa.mobiletest.characters.data.Character
import com.cesarsosa.mobiletest.characters.data.CharacterDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    companion object {
        const val ENDPOINT = "https://gateway.marvel.com:443/v1/public/"
    }

    @GET("characters")
    suspend fun getCharacters(@Query("apikey") apikey: String? = null,
                          @Query("hash") hash: String? = null,
                          @Query("ts") ts: String? = null,
                              @Query("offset") offset: Int? = null,
    ): Response<ResultsResponse<CharacterDTO>>

    @GET("characters/{id}")
    suspend fun getCharacter(@Path("id") id: String,
                             @Query("apikey") apikey: String? = null,
                             @Query("hash") hash: String? = null,
                             @Query("ts") ts: String? = null
    ): Response<ResultsResponse<CharacterDTO>>
}
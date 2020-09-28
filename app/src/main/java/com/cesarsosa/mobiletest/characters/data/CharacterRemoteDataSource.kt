package com.cesarsosa.mobiletest.characters.data

import android.os.Build
import com.cesarsosa.mobiletest.BuildConfig
import com.cesarsosa.mobiletest.api.BaseDataSource
import com.cesarsosa.mobiletest.api.MarvelService
import com.cesarsosa.mobiletest.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class CharacterRemoteDataSource @Inject constructor(private val service: MarvelService) : BaseDataSource() {

    suspend fun fetchCharacters(offset: Int) = getResult {
        service.getCharacters(BuildConfig.APIKEY, BuildConfig.HASH, BuildConfig.TS, offset)
                }
    suspend fun fetchCharacter(id: String)
            = getResult { service.getCharacter(id,BuildConfig.APIKEY, BuildConfig.HASH, BuildConfig.TS) }

}

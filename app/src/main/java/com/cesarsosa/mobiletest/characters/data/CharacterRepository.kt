package com.cesarsosa.mobiletest.characters.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cesarsosa.mobiletest.data.resultLiveData
import com.cesarsosa.mobiletest.testing.OpenForTesting
import com.cesarsosa.mobiletest.util.toCharacter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository module for handling data operations.
 */
@Singleton
@OpenForTesting
class CharacterRepository @Inject constructor(private val dao: CharacterDao,
                                              private val characterRemoteDataSource: CharacterRemoteDataSource) {


    fun observeCharacters(connectivityAvailable: Boolean,
                         coroutineScope: CoroutineScope) =
        if (connectivityAvailable) observeRemoteCharacters(coroutineScope)
        else observeLocalCharacters()

    private fun observeLocalCharacters(): LiveData<PagedList<Character>> {
        val dataSourceFactory = dao.getPagedCharacter()
        return LivePagedListBuilder(dataSourceFactory,
            CharacterPageDataSourceFactory.pagedListConfig()).build()
    }

    private fun observeRemoteCharacters(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Character>> {
        val dataSourceFactory = CharacterPageDataSourceFactory(characterRemoteDataSource,
            dao, ioCoroutineScope)
        return LivePagedListBuilder(dataSourceFactory,
            CharacterPageDataSourceFactory.pagedListConfig()).build()
    }

    fun observeCharacter(id: String) = resultLiveData(
        databaseQuery = { dao.getCharacter(id) },
        networkCall = { characterRemoteDataSource.fetchCharacter(id) },
        saveCallResult = {
            it.data?.results?.first()?.let {it2 ->
                dao.insert(it2.toCharacter())
            }
        })
        .distinctUntilChanged()

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: CharacterRepository? = null

        fun getInstance(dao: CharacterDao, characterRemoteDataSource: CharacterRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: CharacterRepository(dao, characterRemoteDataSource).also { instance = it }
            }
    }

}
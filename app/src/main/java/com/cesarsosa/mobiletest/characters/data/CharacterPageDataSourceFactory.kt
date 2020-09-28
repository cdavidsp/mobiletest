package com.cesarsosa.mobiletest.characters.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CharacterPageDataSourceFactory@Inject constructor(
    private val dataSource: CharacterRemoteDataSource,
    private val dao: CharacterDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Character>() {

    private val liveData = MutableLiveData<CharacterPageDataSource>()

    override fun create(): DataSource<Int, Character> {
        val source = CharacterPageDataSource( dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

}
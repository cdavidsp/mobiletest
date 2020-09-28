package com.cesarsosa.mobiletest.characters.data

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import com.cesarsosa.mobiletest.data.Result;
import com.cesarsosa.mobiletest.util.toCharacter

class CharacterPageDataSource constructor(
    private val dataSource: CharacterRemoteDataSource,
    private val dao: CharacterDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Character>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        fetchData(0, params.requestedLoadSize) {
            callback.onResult(it, null, 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<Character>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchCharacters(page * pageSize)
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!.data!!.results.map { it.toCharacter() };
                dao.insertAll(results)
                callback(results)
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        //networkState.postValue(NetworkState.FAILED)
    }
}
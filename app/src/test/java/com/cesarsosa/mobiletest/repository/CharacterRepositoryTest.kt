package com.cesarsosa.mobiletest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cesarsosa.mobiletest.api.MarvelService
import com.cesarsosa.mobiletest.characters.data.CharacterDao
import com.cesarsosa.mobiletest.characters.data.CharacterRemoteDataSource
import com.cesarsosa.mobiletest.characters.data.CharacterRepository
import com.cesarsosa.mobiletest.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class CharacterRepositoryTest {
    private lateinit var repository: CharacterRepository
    private val dao = mock(CharacterDao::class.java)
    private val service = mock(MarvelService::class.java)
    private val remoteDataSource = CharacterRemoteDataSource(service)
    private val mockRemoteDataSource = spy(remoteDataSource)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.characterDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = CharacterRepository(dao, remoteDataSource)
    }

    @Test
    fun loadCharactersFromNetwork() {
        runBlocking {
            repository.observeCharacters(connectivityAvailable = true,coroutineScope = coroutineScope)

            verify(dao, never()).getCharacters()
            verifyZeroInteractions(dao)
        }
    }

}
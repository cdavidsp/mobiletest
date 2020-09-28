package com.cesarsosa.mobiletest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.cesarsosa.mobiletest.characters.data.Character
import com.cesarsosa.mobiletest.characters.data.CharacterRepository
import com.cesarsosa.mobiletest.characters.ui.CharactersViewModel
import com.cesarsosa.mobiletest.repository.CharacterRepositoryTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class CharactersViewModelTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(CharacterRepository::class.java)

    private var viewModel = CharactersViewModel(repository, coroutineScope)



    @Test
    fun testNull() {
        assertThat(viewModel.connectivityAvailable, notNullValue())
        verify(repository, never()).observeCharacters(false, coroutineScope)
        verify(repository, never()).observeCharacters(true, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObservers() {
        verify(repository, never()).observeCharacters(false, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        viewModel.connectivityAvailable = true

        verify(repository, never()).observeCharacters(true, coroutineScope)
    }


}
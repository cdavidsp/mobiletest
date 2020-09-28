package com.cesarsosa.mobiletest.characters.ui

import androidx.lifecycle.ViewModel
import com.cesarsosa.mobiletest.characters.data.CharacterRepository
import com.cesarsosa.mobiletest.di.CoroutineScropeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class CharactersViewModel  @Inject constructor(private val repository: CharacterRepository,
                                               @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope
)
    : ViewModel() {

    var loading: Boolean = false

    var connectivityAvailable: Boolean = false

    val characters by lazy {
        repository.observeCharacters(
            connectivityAvailable, ioCoroutineScope)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }

}

package com.cesarsosa.mobiletest.characters.ui

import androidx.lifecycle.ViewModel
import com.cesarsosa.mobiletest.characters.data.CharacterRepository
import javax.inject.Inject
/**
 * The ViewModel used in [CharacterFragment].
 */
class CharacterViewModel @Inject constructor(repository: CharacterRepository) : ViewModel() {

    lateinit var id: String

    val character by lazy { repository.observeCharacter(id) }

}

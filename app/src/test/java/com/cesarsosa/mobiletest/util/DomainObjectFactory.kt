package com.cesarsosa.mobiletest.util

import com.cesarsosa.mobiletest.characters.data.Character
import com.cesarsosa.mobiletest.characters.data.CharacterDTO
import com.cesarsosa.mobiletest.characters.data.CharacterDataDTO
import com.cesarsosa.mobiletest.characters.data.Thumbnail


object DomainObjectFactory {

    fun createCharacterDTO() = CharacterDTO("id_123", "Character Name", "", ""
        , Thumbnail("",""),
        CharacterDataDTO(0,0, null),
        CharacterDataDTO(0,0, null),
        CharacterDataDTO(0,0, null),
        CharacterDataDTO(0,0, null),
        null)

    fun createCharacterDTOs(count: Int): List<CharacterDTO> {
        return (0 until count).map {
            createCharacterDTO()
        }
    }

}

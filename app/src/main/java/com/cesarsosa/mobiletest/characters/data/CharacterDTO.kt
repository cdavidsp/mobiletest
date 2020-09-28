package com.cesarsosa.mobiletest.characters.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CharacterDTO(

    val id: String,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail,

    val comics: CharacterDataDTO,
    val series: CharacterDataDTO,
    val stories: CharacterDataDTO,
    val events: CharacterDataDTO,
    val urls: List<CharacterDataItemDTO>?

) {
    override fun toString() = name
}
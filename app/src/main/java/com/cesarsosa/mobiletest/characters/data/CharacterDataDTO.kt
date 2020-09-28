package com.cesarsosa.mobiletest.characters.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CharacterDataDTO(

    val available: Int,
    val returned: Int,
    val items: List<CharacterDataItemDTO>?

) {
    override fun toString() = ""+returned
}
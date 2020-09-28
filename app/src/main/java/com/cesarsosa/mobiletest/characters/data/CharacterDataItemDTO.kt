package com.cesarsosa.mobiletest.characters.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CharacterDataItemDTO(

    val name: String? = "",
    val type: String? = "",
    val url: String? = ""

) {
    override fun toString() = name!!
}
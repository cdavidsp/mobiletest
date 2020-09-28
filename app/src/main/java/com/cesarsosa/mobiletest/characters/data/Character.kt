package com.cesarsosa.mobiletest.characters.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("imageURL")
    val imageURL: String,
    @field:SerializedName("comics")
    val comics: String,
    @field:SerializedName("series")
    val series: String,
    @field:SerializedName("stories")
    val stories: String,
    @field:SerializedName("events")
    val events: String,
    @field:SerializedName("urlDetail")
    val urlDetail: String,
    @field:SerializedName("urlWiki")
    val urlWiki: String,
    @field:SerializedName("urlComicLink")
    val urlComicLink: String

) {
    override fun toString() = name
}
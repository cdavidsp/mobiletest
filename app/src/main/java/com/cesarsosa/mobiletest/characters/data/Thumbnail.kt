package com.cesarsosa.mobiletest.characters.data


data class Thumbnail(
    val path: String,

    val extension: String
) {
    fun getUrl() = "$path.$extension".replace("http://", "https://")
}
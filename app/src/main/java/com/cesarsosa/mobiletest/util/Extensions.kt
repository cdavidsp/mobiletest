package com.cesarsosa.mobiletest.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cesarsosa.mobiletest.characters.data.Character
import com.cesarsosa.mobiletest.characters.data.CharacterDTO

fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar!!.title = title
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun CharacterDTO.toCharacter(): Character{

        var comicsString = ""
        var seriesString = ""
        var storiesString = ""
        var eventsString = ""

        var urlDetail = ""
        var urlWiki = ""
        var urlComicLink = ""

        if (comics.returned > 0) {
            comicsString = comics.items?.joinToString { it.name.toString() } ?:""
        }
        if (series.returned > 0) {
            seriesString = series.items?.joinToString { it.name.toString() } ?:""
        }
        if (stories.returned > 0) {
            storiesString = stories.items?.joinToString { it.name.toString() } ?:""
        }
        if (events.returned > 0) {
            eventsString = events.items?.joinToString { it.name.toString() } ?:""
        }

    if (urls?.isNotEmpty()!!) {
        urlDetail = urls.findLast { it.type.equals("detail") }?.url ?: ""
        urlWiki = urls.findLast { it.type.equals("wiki") }?.url ?: ""
        urlComicLink = urls.findLast { it.type.equals("comiclink") }?.url ?: ""
    }
        return Character(id,name, description, thumbnail.getUrl(),comicsString, seriesString, storiesString, eventsString, urlDetail, urlWiki, urlComicLink )

}
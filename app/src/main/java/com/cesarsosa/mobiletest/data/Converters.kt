package com.cesarsosa.mobiletest.data

import androidx.room.TypeConverter
import com.cesarsosa.mobiletest.characters.data.Character
import com.cesarsosa.mobiletest.characters.data.CharacterDTO
import java.util.*


/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }


}
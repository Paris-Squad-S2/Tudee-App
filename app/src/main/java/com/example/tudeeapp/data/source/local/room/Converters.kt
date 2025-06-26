package com.example.tudeeapp.data.source.local.room

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class Converters {

    @TypeConverter
    fun fromLocalData(date: LocalDateTime?): String?{
        return  date?.toString()
    }
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.parse(it) }
    }
}
package com.teammovil.pettracker.data.database

import androidx.room.TypeConverter
import java.util.*

class ConvertersForDate {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
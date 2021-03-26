package com.teammovil.pettracker

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "yyyy-MM-dd"

fun getDateFromString (date: String?): Date? {
    return try {
        SimpleDateFormat(DATE_FORMAT).parse(date)
    }catch (e: Exception){
        null
    }
}

fun getStringFromDate (date: Date?): String? {
    return try {
        SimpleDateFormat(DATE_FORMAT).format(date)
    }catch (e: Exception){
        null
    }
}
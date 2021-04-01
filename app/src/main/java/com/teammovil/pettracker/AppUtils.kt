package com.teammovil.pettracker

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "yyyy-MM-dd"

fun getDateFromString (date: String?): Date? {
    return try {
        date?.let {SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(it)}
    }catch (e: Exception){
        null
    }
}

fun getStringFromDate (date: Date?): String? {
    return try {
        date?.let {SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)}
    }catch (e: Exception){
        null
    }
}
package com.example.googlegpsapp.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormattedDateTimeString(): String {
    val pattern = "MM/dd/yyyy hh:mm"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}

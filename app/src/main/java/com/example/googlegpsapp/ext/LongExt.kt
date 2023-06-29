package com.example.googlegpsapp.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.timestampToFormattedDateTime(): String {
    val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.getDefault())
    val dateTime = Date(this)
    return dateFormat.format(dateTime)
}

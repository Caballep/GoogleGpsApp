package com.example.googlegpsapp.presentation.screen.landing.data

import java.util.Date

data class DetailedLocationModel (
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val dateTime: Date
)

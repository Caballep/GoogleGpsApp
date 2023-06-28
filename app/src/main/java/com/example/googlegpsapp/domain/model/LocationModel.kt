package com.example.googlegpsapp.domain.model

import android.location.Location

data class LocationModel(
    val latitude: Double,
    val longitude: Double,
    val time: Long
) {
    companion object {
        fun fromLocation(location: Location) = LocationModel(
            latitude = location.latitude,
            longitude = location.longitude,
            time = location.time
        )
    }
}

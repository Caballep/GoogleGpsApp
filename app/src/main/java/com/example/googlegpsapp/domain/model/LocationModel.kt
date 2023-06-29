package com.example.googlegpsapp.domain.model

import com.example.googlegpsapp.data.source.db.entity.LocationEntity
import com.example.googlegpsapp.ext.timestampToFormattedDateTime

data class LocationModel(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val formattedTime: String
) {
    companion object {
        fun from(locationEntity: LocationEntity) = LocationModel(
            id = locationEntity.id,
            name = locationEntity.name,
            latitude = locationEntity.latitude,
            longitude = locationEntity.longitude,
            formattedTime = locationEntity.locationTimestamp.timestampToFormattedDateTime()
        )
    }
}

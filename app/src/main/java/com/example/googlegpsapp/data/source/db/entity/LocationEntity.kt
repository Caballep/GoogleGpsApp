package com.example.googlegpsapp.data.source.db.entity

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val locationTimestamp: Long,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {

    companion object {
        fun from(name: String, location: Location): LocationEntity =
            LocationEntity(
                name = name,
                latitude = location.latitude,
                longitude = location.longitude,
                locationTimestamp = location.time
            )
    }

}

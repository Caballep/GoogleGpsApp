package com.example.googlegpsapp.data.source.entity

import android.location.Location
import com.example.googlegpsapp.data.source.db.entity.LocationEntity
import io.mockk.*
import org.junit.Test

class LocationEntityTest {

    @Test
    fun `from name and location`() {
        val locationName = "Cool Location"
        val location: Location = mockk()

        every { location.latitude } returns 10.10
        every { location.longitude } returns 20.20
        every { location.time } returns 30303030


        val locationEntityFrom = LocationEntity.from(
            name = locationName,
            location = location
        )

        val expectedLocationEntity = LocationEntity(
            name = "Cool Location",
            latitude = 10.10,
            longitude = 20.20,
            locationTimestamp = 30303030
        )

        assert(locationEntityFrom == expectedLocationEntity)
    }
}
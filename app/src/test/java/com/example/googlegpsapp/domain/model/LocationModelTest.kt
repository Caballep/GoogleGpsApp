package com.example.googlegpsapp.domain.model

import com.example.googlegpsapp.core.ext.timestampToFormattedDateTime
import com.example.googlegpsapp.data.source.db.entity.LocationEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class LocationModelTest {

    @Test
    fun `from name and location`() {
        val locationEntity: LocationEntity = mockk()

        every { locationEntity.id } returns 56
        every { locationEntity.name } returns "My favorite place"
        every { locationEntity.latitude } returns 10.10
        every { locationEntity.longitude } returns 20.20
        every { locationEntity.locationTimestamp } returns 30303030

        val locationModelFrom = LocationModel.from(
            locationEntity = locationEntity
        )

        val expectedLocationModel = LocationModel(
            id = 56,
            name = "My favorite place",
            latitude = 10.10,
            longitude = 20.20,
            formattedTime = (30303030).toLong().timestampToFormattedDateTime()
        )

        assert(locationModelFrom == expectedLocationModel)
    }
}

package com.example.googlegpsapp.data.repository

import android.location.Location
import com.example.googlegpsapp.core.ext.timestampToFormattedDateTime
import com.example.googlegpsapp.data.source.db.GoogleGpsAppDao
import com.example.googlegpsapp.data.source.db.entity.LocationEntity
import com.example.googlegpsapp.data.source.device.LocationProvider
import com.google.android.gms.tasks.Tasks
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocationRepositoryTest {

    private lateinit var locationProvider: LocationProvider
    private lateinit var googleGpsAppDao: GoogleGpsAppDao
    private lateinit var locationRepository: LocationRepository

    @Before
    fun setup() {
        locationProvider = mockk()
        googleGpsAppDao = mockk()
        locationRepository = LocationRepository(locationProvider, googleGpsAppDao)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `saveLocation inserts location`() = runBlocking {
        val locationName = "Test Location"
        val location: Location = mockk()

        every { location.latitude } returns 10.10
        every { location.longitude } returns 20.20
        every { location.time } returns 30303030

        coEvery { locationProvider.getCurrentLocation() } returns Tasks.forResult(location)

        coEvery {
            googleGpsAppDao.insertLocation(any())
        } just Runs

        val result = locationRepository.saveLocation(locationName)

        coVerify { locationProvider.getCurrentLocation() }
        coVerify { googleGpsAppDao.insertLocation(any()) }

        assert(result.isSuccess)
        assert(result.getOrNull() == Unit)
        assert(result.exceptionOrNull() == null)
    }

    @Test
    fun `saveLocation locationEntity is the expected`() = runBlocking {
        val locationName = "Test Location"
        val location: Location = mockk()

        every { location.latitude } returns 10.10
        every { location.longitude } returns 20.20
        every { location.time } returns 30303030

        coEvery { locationProvider.getCurrentLocation() } returns Tasks.forResult(location)

        val expectedEntity = LocationEntity(
            name = "Test Location",
            latitude = 10.10,
            longitude = 20.20,
            locationTimestamp = 30303030
        )

        coEvery {
            googleGpsAppDao.insertLocation(expectedEntity)
        } just Runs

        val result = locationRepository.saveLocation(locationName)

        coVerify { locationProvider.getCurrentLocation() }
        coVerify { googleGpsAppDao.insertLocation(expectedEntity) }

        assert(result.isSuccess)
        assert(result.getOrNull() == Unit)
        assert(result.exceptionOrNull() == null)
    }

    @Test
    fun `saveLocation getCurrentLocation throws`() = runBlocking {
        val locationName = "Test Location"
        val location: Location = mockk()

        every { location.latitude } returns 10.10
        every { location.longitude } returns 20.20
        every { location.time } returns 30303030

        coEvery { locationProvider.getCurrentLocation() } throws Throwable()

        val result = locationRepository.saveLocation(locationName)

        coVerify { locationProvider.getCurrentLocation() }
        coVerify(exactly = 0) { googleGpsAppDao.insertLocation(any()) }

        assert(result.isFailure)
        assert(result.getOrNull() == null)
        assert(result.exceptionOrNull() is Throwable)
    }

    @Test
    fun `saveLocation insertLocation throws`() = runBlocking {
        val locationName = "Test Location"
        val location: Location = mockk()

        every { location.latitude } returns 10.10
        every { location.longitude } returns 20.20
        every { location.time } returns 30303030

        coEvery { locationProvider.getCurrentLocation() } returns Tasks.forResult(location)
        coEvery {
            googleGpsAppDao.insertLocation(any())
        } throws Throwable()

        val result = locationRepository.saveLocation(locationName)

        coVerify { locationProvider.getCurrentLocation() }
        coVerify { googleGpsAppDao.insertLocation(any()) }

        assert(result.isFailure)
        assert(result.getOrNull() == null)
        assert(result.exceptionOrNull() is Throwable)
    }

    @Test
    fun `deleteLocation deletes location`() = runBlocking {
        val locationId = 123

        coEvery {
            googleGpsAppDao.deleteLocation(locationId)
        } just Runs

        val result = locationRepository.deleteLocation(locationId)

        coVerify { googleGpsAppDao.deleteLocation(locationId = locationId) }

        assert(result.isSuccess)
        assert(result.getOrNull() == Unit)
        assert(result.exceptionOrNull() == null)
    }

    @Test
    fun `deleteLocation throws`() = runBlocking {
        val locationId = 123

        coEvery {
            googleGpsAppDao.deleteLocation(locationId)
        } throws Throwable()

        val result = locationRepository.deleteLocation(locationId)

        coVerify { googleGpsAppDao.deleteLocation(locationId = locationId) }

        assert(result.isFailure)
        assert(result.getOrNull() == null)
        assert(result.exceptionOrNull() is Throwable)
    }

    @Test
    fun `getLocationModels retrieves success`() = runBlocking {

        val locationEntities = listOf(
            LocationEntity("Location 1", 10.0, 20.0, 1625645768000, 1),
            LocationEntity("Location 2", 30.0, 40.0, 1625645790000, 2)
        )

        coEvery {
            googleGpsAppDao.getLocationEntities()
        } returns locationEntities

        val result = locationRepository.getLocationModels()

        coVerify { googleGpsAppDao.getLocationEntities() }

        assert(result.isSuccess)
        assert(result.getOrNull() != null)
        assert(result.exceptionOrNull() == null)
    }

    @Test
    fun `getLocationModels retrieves expected locationModels`() = runBlocking {

        val locationEntities = listOf(
            LocationEntity("Location 1", 10.0, 20.0, 1625645768000, 1),
            LocationEntity("Location 2", 140.0, 220.0, 1234234768000, 2),
            LocationEntity("Location 3", 130.0, 230.0, 1653545768000, 3),
            LocationEntity("Location 4", 110.0, 240.0, 5455768000, 4)
        )

        coEvery {
            googleGpsAppDao.getLocationEntities()
        } returns locationEntities

        val result = locationRepository.getLocationModels()

        val locationModels = result.getOrNull()!!
        assert(locationModels.size == 4)
        assert(locationModels[0].id == 1)
        assert(locationModels[0].latitude == 10.0)
        assert(locationModels[0].longitude == 20.0)
        assert(locationModels[0].formattedTime == (1625645768000).toLong().timestampToFormattedDateTime())
        assert(locationModels[0].id == 1)
    }

    @Test
    fun `getLocationModels throws`() = runBlocking {
        coEvery {
            googleGpsAppDao.getLocationEntities()
        } throws Throwable()

        val result = locationRepository.getLocationModels()

        coVerify { googleGpsAppDao.getLocationEntities() }

        assert(result.isFailure)
        assert(result.getOrNull() == null)
        assert(result.exceptionOrNull() is Throwable)
    }

}

package com.example.googlegpsapp.domain.usecase

import android.database.sqlite.SQLiteAbortException
import com.example.googlegpsapp.core.EmptyLocationException
import com.example.googlegpsapp.data.repository.LocationRepository
import com.example.googlegpsapp.domain.propagation.ErrorType
import com.example.googlegpsapp.domain.propagation.Outcome
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SaveLocationUseCaseTest {

    private lateinit var locationRepository: LocationRepository
    private lateinit var saveLocationUseCase: SaveLocationUseCase

    @Before
    fun setup() {
        locationRepository = mockk()
        saveLocationUseCase = SaveLocationUseCase(locationRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke success`() = runBlocking {
        val locationName = "Somewhere in the rainbow"
        coEvery { locationRepository.saveLocation(locationName) } returns Unit

        val result = saveLocationUseCase(locationName)

        coVerify { locationRepository.saveLocation(locationName) }

        assert(result is Outcome.Success)
    }

    @Test
    fun `invoke failure no_permissions`() = runBlocking {
        val locationName = "Somewhere in the rainbow"
        coEvery { locationRepository.saveLocation(locationName) } returns Unit

        val result = saveLocationUseCase(locationName)

        coVerify { locationRepository.saveLocation(locationName) }

        val resultError = (result as Outcome.Error).errorType

        assert(resultError == ErrorType.LOCATION_NO_PERMISSIONS)
    }

    @Test
    fun `invoke failure sql_error`() = runBlocking {
        val locationName = "Somewhere in the rainbow"
        coEvery { locationRepository.saveLocation(locationName) } returns Unit

        val result = saveLocationUseCase(locationName)

        coVerify { locationRepository.saveLocation(locationName) }

        val resultError = (result as Outcome.Error).errorType

        assert(resultError == ErrorType.SQL_ERROR)
    }

    @Test
    fun `invoke failure location_empty_data`() = runBlocking {
        val locationName = "Somewhere in the rainbow"
        coEvery { locationRepository.saveLocation(locationName) } returns Unit

        val result = saveLocationUseCase(locationName)

        coVerify { locationRepository.saveLocation(locationName) }

        val resultError = (result as Outcome.Error).errorType

        assert(resultError == ErrorType.LOCATION_EMPTY_DATA)
    }

    @Test
    fun `invoke failure unknown`() = runBlocking {
        val locationName = "Somewhere in the rainbow"
        coEvery { locationRepository.saveLocation(locationName) } returns Unit

        val result = saveLocationUseCase(locationName)

        coVerify { locationRepository.saveLocation(locationName) }

        val resultError = (result as Outcome.Error).errorType

        assert(resultError == ErrorType.UNKNOWN)
    }
}

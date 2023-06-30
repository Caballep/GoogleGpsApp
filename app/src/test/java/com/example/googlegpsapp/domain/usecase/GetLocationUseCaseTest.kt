package com.example.googlegpsapp.domain.usecase

import android.database.sqlite.SQLiteAccessPermException
import com.example.googlegpsapp.data.repository.LocationRepository
import com.example.googlegpsapp.domain.model.LocationModel
import com.example.googlegpsapp.domain.util.ErrorType
import com.example.googlegpsapp.domain.util.Outcome
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetLocationUseCaseTest {

    private lateinit var locationRepository: LocationRepository
    private lateinit var getLocationsUseCase: GetLocationsUseCase

    @Before
    fun setup() {
        locationRepository = mockk()
        getLocationsUseCase = GetLocationsUseCase(locationRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke getLocationModels success`() = runBlocking {
        coEvery {  locationRepository.getLocationModels() } returns Result.success(emptyList())

        val result = getLocationsUseCase()

        coVerify { locationRepository.getLocationModels() }

        assert(result is Outcome.Success)
    }

    @Test
    fun `invoke success expected data`() = runBlocking {
        val getLocationsModelList = listOf(
            LocationModel(id = 1, name = "Favorite Tree", 50.50, 60.60, "10/10/2010 10:10"),
            LocationModel(id = 2, name = "Red Meh", 150.50, 10.60, "10/10/2010 10:10"),
            LocationModel(id = 3, name = "One O One", 30.50, 20.60, "10/10/2010 10:10")
        )

        coEvery {  locationRepository.getLocationModels() } returns Result.success(getLocationsModelList)

        val result = getLocationsUseCase()
        val resultData = (result as Outcome.Success).data

        assert(resultData.size == 3)
        assert(resultData[0].id == 1)
        assert(resultData[1].name == "Red Meh")
        assert(resultData[2].latitude == 30.50)
    }

    @Test
    fun `invoke getLocationModels failure sql_error`() = runBlocking {
        coEvery {  locationRepository.getLocationModels() } returns Result.failure(
            SQLiteAccessPermException()
        )

        val result = getLocationsUseCase()

        coVerify { locationRepository.getLocationModels() }

        val resultErrorType = (result as Outcome.Error).errorType

        assert(resultErrorType == ErrorType.SQL_ERROR)
    }

    @Test
    fun `invoke getLocationModels failure unknown`() = runBlocking {
        coEvery {  locationRepository.getLocationModels() } returns Result.failure(
            Exception()
        )

        val result = getLocationsUseCase()

        coVerify { locationRepository.getLocationModels() }

        val resultErrorType = (result as Outcome.Error).errorType

        assert(resultErrorType == ErrorType.UNKNOWN)
    }
}
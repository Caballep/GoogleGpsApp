package com.example.googlegpsapp.domain.usecase

import android.database.sqlite.SQLiteAbortException
import com.example.googlegpsapp.data.repository.LocationRepository
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

class DeleteLocationUseCaseTest {

    private lateinit var locationRepository: LocationRepository
    private lateinit var deleteLocationUseCase: DeleteLocationUseCase

    @Before
    fun setup() {
        locationRepository = mockk()
        deleteLocationUseCase = DeleteLocationUseCase(locationRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke deleteLocation success`() = runBlocking {
        coEvery { locationRepository.deleteLocation(any()) } returns Result.success(Unit)

        val result = deleteLocationUseCase(1)

        coVerify { locationRepository.deleteLocation(any()) }

        assert(result is Outcome.Success)
        assert((result as Outcome.Success).data == Unit)
    }

    @Test
    fun `invoke deleteLocation failure sql_error`() = runBlocking {
        coEvery { locationRepository.deleteLocation(any()) } returns Result.failure(
            SQLiteAbortException()
        )

        val result = deleteLocationUseCase(1)

        coVerify { locationRepository.deleteLocation(any()) }

        assert(result is Outcome.Error)
        assert((result as Outcome.Error).errorType == ErrorType.SQL_ERROR)
    }

    @Test
    fun `invoke deleteLocation failure unknown`() = runBlocking {
        coEvery { locationRepository.deleteLocation(any()) } returns Result.failure(
            Exception()
        )

        val result = deleteLocationUseCase(1)

        coVerify { locationRepository.deleteLocation(any()) }

        assert(result is Outcome.Error)
        assert((result as Outcome.Error).errorType == ErrorType.UNKNOWN)
    }
}

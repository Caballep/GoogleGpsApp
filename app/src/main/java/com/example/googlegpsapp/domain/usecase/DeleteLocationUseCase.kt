package com.example.googlegpsapp.domain.usecase

import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteAccessPermException
import android.database.sqlite.SQLiteDoneException
import com.example.googlegpsapp.data.repository.LocationRepository
import com.example.googlegpsapp.domain.util.ErrorType
import com.example.googlegpsapp.domain.util.Outcome
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(id: Int): Outcome<Unit> {
        val result = locationRepository.deleteLocation(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()
            if (e is SQLiteAbortException || e is SQLiteAccessPermException || e is SQLiteDoneException) {
                return Outcome.Error(ErrorType.SQL_ERROR)
            }
            return Outcome.Error(ErrorType.UNKNOWN)
        }
        return Outcome.Success(Unit)
    }
}

package com.example.googlegpsapp.domain.usecase

import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteAccessPermException
import android.database.sqlite.SQLiteDoneException
import com.example.googlegpsapp.core.EmptyLocationException
import com.example.googlegpsapp.data.repository.LocationRepository
import com.example.googlegpsapp.domain.propagation.ErrorType
import com.example.googlegpsapp.domain.propagation.Outcome
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(name: String): Outcome<Unit> {
        try {
            locationRepository.saveLocation(name)
        } catch (e: Throwable) {
            if (e is SecurityException) {
                return Outcome.Error(ErrorType.LOCATION_NO_PERMISSIONS)
            }
            if (e is SQLiteAbortException || e is SQLiteAccessPermException || e is SQLiteDoneException) {
                return Outcome.Error(ErrorType.SQL_ERROR)
            }
            if (e is EmptyLocationException) {
                return Outcome.Error(ErrorType.LOCATION_EMPTY_DATA)
            }
            return Outcome.Error(ErrorType.UNKNOWN)
        }
        return Outcome.Success(Unit)
    }
}

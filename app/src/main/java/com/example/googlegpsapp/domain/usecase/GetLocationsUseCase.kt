package com.example.googlegpsapp.domain.usecase

import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteAccessPermException
import android.database.sqlite.SQLiteDoneException
import com.example.googlegpsapp.domain.propagation.ErrorType
import com.example.googlegpsapp.data.repository.LocationRepository
import com.example.googlegpsapp.domain.model.LocationModel
import com.example.googlegpsapp.domain.propagation.Outcome

import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Outcome<List<LocationModel>> {
        val result = locationRepository.getLocationModels()

        if (result.isFailure) {
            val e = result.exceptionOrNull()
            if (e is SQLiteAbortException || e is SQLiteAccessPermException || e is SQLiteDoneException) {
                return Outcome.Error(ErrorType.SQL_ERROR)
            }
            return Outcome.Error(ErrorType.UNKNOWN)
        }

        val locationModels = result.getOrDefault(emptyList())
        return Outcome.Success(locationModels)
    }
}

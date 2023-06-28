package com.example.googlegpsapp.data.repository

import com.example.googlegpsapp.core.ErrorType
import com.example.googlegpsapp.data.source.device.LocationProvider
import com.example.googlegpsapp.domain.model.LocationModel
import kotlinx.coroutines.tasks.await
import com.example.googlegpsapp.core.Result
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationProvider: LocationProvider,
) {

    suspend fun getLocationModel(): Result<LocationModel> {
        return try {
            val location = locationProvider.getCurrentLocation().await()
            Result.Success(
                LocationModel.fromLocation(location)
            )
        } catch (e: Exception) {
            if (e is SecurityException) {
                Result.Error<ErrorType>(ErrorType.LOCATION_NO_PERMISSIONS)
            }
            Result.Error(ErrorType.UNKNOWN)
        }
    }

}

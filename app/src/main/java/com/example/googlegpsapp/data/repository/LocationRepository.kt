package com.example.googlegpsapp.data.repository

import android.location.Location
import com.example.googlegpsapp.core.EmptyLocationException
import com.example.googlegpsapp.data.source.device.LocationProvider
import com.example.googlegpsapp.domain.model.LocationModel
import kotlinx.coroutines.tasks.await
import com.example.googlegpsapp.data.source.db.GoogleGpsAppDao
import com.example.googlegpsapp.data.source.db.entity.LocationEntity
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationProvider: LocationProvider,
    private val googleGpsAppDao: GoogleGpsAppDao
) {

    suspend fun saveLocation(locationName: String): Result<Unit> {
        return try {
            with(getLocation()) {
                googleGpsAppDao.insertLocation(
                    LocationEntity.from(
                        name = locationName,
                        location = this
                    )
                )
            }
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun deleteLocation(locationId: Int): Result<Unit> {
        return try {
            googleGpsAppDao.deleteLocation(locationId = locationId)
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun getLocationModels(): Result<List<LocationModel>> {
        return try {
            val locationEntities = googleGpsAppDao.getLocationEntities()
            val locations = locationEntities.map { entity ->
                LocationModel.from(entity)
            }
            Result.success(locations)

        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    private suspend fun getLocation(): Location =
        locationProvider.getCurrentLocation().await() ?: throw EmptyLocationException()


}

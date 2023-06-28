package com.example.googlegpsapp.domain.usecase

import com.example.googlegpsapp.core.ErrorType
import com.example.googlegpsapp.data.repository.LocationRepository
import com.example.googlegpsapp.core.Result
import com.example.googlegpsapp.presentation.screen.landing.data.DetailedLocationModel
import java.util.*
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Result<DetailedLocationModel> {
        val locationModel = locationRepository.getLocationModel()

        locationModel.data?.let {
            return Result.Success(
                DetailedLocationModel(
                latitude = it.latitude,
                longitude = it.longitude,
                dateTime = Date(it.time))
            )
        } ?: run {
            return Result.Error(ErrorType.EMPTY_DATA)
        }
    }
}

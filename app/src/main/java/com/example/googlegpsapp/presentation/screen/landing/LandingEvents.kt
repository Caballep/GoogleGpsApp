package com.example.googlegpsapp.presentation.screen.landing

import com.example.googlegpsapp.domain.model.LocationModel

sealed class LocationProcessingEvent {
    object Initial : LocationProcessingEvent()
    object Processing : LocationProcessingEvent()
    object Done : LocationProcessingEvent()
    object LocationPermissionsError : LocationProcessingEvent()
    object Error : LocationProcessingEvent()
}

sealed class LocationsEvent {
    object Initial : LocationsEvent()
    object Loading : LocationsEvent()
    data class Data(val locations: List<LocationModel>) : LocationsEvent()
    object NoData : LocationsEvent()
    object Error: LocationsEvent()
}


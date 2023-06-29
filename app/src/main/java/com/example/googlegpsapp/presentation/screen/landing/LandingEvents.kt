package com.example.googlegpsapp.presentation.screen.landing

import com.example.googlegpsapp.domain.model.LocationModel

sealed class SaveLocationEvent {
    object Initial : SaveLocationEvent()
    object Processing : SaveLocationEvent()
    object Done : SaveLocationEvent()
    object LocationPermissionsError : SaveLocationEvent()
    object Error : SaveLocationEvent()
}

sealed class LocationsEvent {
    object Initial : LocationsEvent()
    object Loading : LocationsEvent()
    data class Locations(val locations: List<LocationModel>) : LocationsEvent()
    object Error: LocationsEvent()
}


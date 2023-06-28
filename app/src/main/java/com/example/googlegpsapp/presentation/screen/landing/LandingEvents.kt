package com.example.googlegpsapp.presentation.screen.landing

import com.example.googlegpsapp.presentation.screen.landing.data.DetailedLocationModel

sealed class LandingEvents {
    object Initial : LandingEvents()
    object GettingDetailedLocationModel : LandingEvents()
    data class NewDetailedLocationModel(val detailedLocationModel: DetailedLocationModel) : LandingEvents()
    object ErrorGettingDetailedLocationModel : LandingEvents()
    object SavingLocation
    object LocationSaved
    object NewErrorSavingLocation
    object GettingSavedLocations
    object SavedLocations
    object ErrorGettingSavedLocations
}

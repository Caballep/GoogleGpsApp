package com.example.googlegpsapp.data.source.device

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task

class LocationProvider(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Task<Location?> {
        return fusedLocationProviderClient.lastLocation
    }
}

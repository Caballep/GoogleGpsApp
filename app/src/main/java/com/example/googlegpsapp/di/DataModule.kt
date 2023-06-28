package com.example.googlegpsapp.di

import android.app.Application
import com.example.googlegpsapp.data.source.device.LocationProvider
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun locationProvider(application: Application): LocationProvider {
        return LocationProvider(
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
        )
    }

}

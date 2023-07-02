package com.example.googlegpsapp

import com.example.googlegpsapp.data.source.db.GoogleGpsAppDatabase
import android.app.Application
import androidx.room.Room
import com.example.googlegpsapp.data.source.db.GoogleGpsAppDao
import com.example.googlegpsapp.data.source.device.LocationProvider
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun locationProvider(application: Application): LocationProvider {
        return LocationProvider(
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
        )
    }

    @Provides
    @Singleton
    fun provideGoogleGpsAppDatabase(application: Application): GoogleGpsAppDatabase {
        return Room.databaseBuilder(
            application,
            GoogleGpsAppDatabase::class.java,
            "google_gps_app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGoogleGpsAppDao(database: GoogleGpsAppDatabase): GoogleGpsAppDao {
        return database.googleGpsAppDao()
    }
}

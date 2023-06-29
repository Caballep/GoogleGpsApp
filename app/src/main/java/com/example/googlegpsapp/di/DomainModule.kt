package com.example.googlegpsapp.di

import com.example.googlegpsapp.data.repository.LocationRepository
import com.example.googlegpsapp.domain.usecase.GetLocationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun getWordInfoUseCase(repository: LocationRepository): GetLocationsUseCase {
        return GetLocationsUseCase(repository)
    }

}


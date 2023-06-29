package com.example.googlegpsapp.presentation.screen.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlegpsapp.domain.usecase.DeleteLocationUseCase
import com.example.googlegpsapp.domain.usecase.GetLocationsUseCase
import com.example.googlegpsapp.domain.usecase.SaveLocationUseCase
import com.example.googlegpsapp.domain.util.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.googlegpsapp.domain.util.Outcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val saveLocationUseCase: SaveLocationUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val getLocationUseCase: GetLocationsUseCase
) : ViewModel() {

    private var locationProcessingJob: Job? = null
    private var getLocationJob: Job? = null

    private val _locationProcessingEvent = MutableStateFlow<LocationProcessingEvent>(LocationProcessingEvent.Initial)
    val locationProcessingEvent: StateFlow<LocationProcessingEvent> = _locationProcessingEvent.asStateFlow()
    private fun emitSaveLocationEvent(event: LocationProcessingEvent) {
        _locationProcessingEvent.value = event
    }

    private val _locationsEvent = MutableStateFlow<LocationsEvent>(LocationsEvent.Initial)
    val locationsEvent: StateFlow<LocationsEvent> = _locationsEvent.asStateFlow()
    private fun emitLocationsEvent(event: LocationsEvent) {
        _locationsEvent.value = event
    }

    fun saveLocation(name: String) {
        locationProcessingJob?.cancel()
        emitSaveLocationEvent(LocationProcessingEvent.Processing)
        locationProcessingJob = viewModelScope.launch {
            when (val result = saveLocationUseCase.invoke(name)) {
                is Outcome.Success -> {
                    emitSaveLocationEvent(LocationProcessingEvent.Done)
                }
                is Outcome.Error -> {
                    if (result.errorType == ErrorType.LOCATION_NO_PERMISSIONS) {
                        emitSaveLocationEvent(LocationProcessingEvent.LocationPermissionsError)
                    }
                    emitSaveLocationEvent(LocationProcessingEvent.Error)
                }
            }
        }
    }

    fun deleteLocation(id: Int) {
        locationProcessingJob?.cancel()
        emitSaveLocationEvent(LocationProcessingEvent.Processing)
        locationProcessingJob = viewModelScope.launch {
            when (val result = deleteLocationUseCase.invoke(id)) {
                is Outcome.Success -> {
                    emitSaveLocationEvent(LocationProcessingEvent.Done)
                }
                is Outcome.Error -> {
                    if (result.errorType == ErrorType.LOCATION_NO_PERMISSIONS) {
                        emitSaveLocationEvent(LocationProcessingEvent.LocationPermissionsError)
                    }
                    emitSaveLocationEvent(LocationProcessingEvent.Error)
                }
            }
        }
    }

    fun fetchLocation() {
        getLocationJob?.cancel()
        emitLocationsEvent(LocationsEvent.Loading)
        getLocationJob = viewModelScope.launch {
            when(val result = getLocationUseCase.invoke()) {
                is Outcome.Success -> {
                    if (result.data.isEmpty()) {
                        emitLocationsEvent(LocationsEvent.NoData)
                    }
                    emitLocationsEvent(LocationsEvent.Data(result.data))
                }
                is Outcome.Error -> {
                    emitLocationsEvent(LocationsEvent.Error)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationProcessingJob?.cancel()
        getLocationJob?.cancel()
    }
}

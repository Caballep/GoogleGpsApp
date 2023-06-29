package com.example.googlegpsapp.presentation.screen.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getLocationUseCase: GetLocationsUseCase
) : ViewModel() {

    private var saveLocationJob: Job? = null
    private var getLocationJob: Job? = null

    private val _saveLocationEvent = MutableStateFlow<SaveLocationEvent>(SaveLocationEvent.Initial)
    val saveLocationEvent: StateFlow<SaveLocationEvent> = _saveLocationEvent.asStateFlow()
    private fun emitSaveLocationEvent(event: SaveLocationEvent) {
        _saveLocationEvent.value = event
    }

    private val _locationsEvent = MutableStateFlow<LocationsEvent>(LocationsEvent.Initial)
    val locationsEvent: StateFlow<LocationsEvent> = _locationsEvent.asStateFlow()
    private fun emitLocationsEvent(event: LocationsEvent) {
        _locationsEvent.value = event
    }

    fun saveLocation(name: String) {
        saveLocationJob?.cancel()
        emitSaveLocationEvent(SaveLocationEvent.Processing)
        saveLocationJob = viewModelScope.launch {
            when (val result = saveLocationUseCase.invoke(name)) {
                is Outcome.Success -> {
                    emitSaveLocationEvent(SaveLocationEvent.Done)
                }
                is Outcome.Error -> {
                    if (result.errorType == ErrorType.LOCATION_NO_PERMISSIONS) {
                        emitSaveLocationEvent(SaveLocationEvent.LocationPermissionsError)
                    }
                    emitSaveLocationEvent(SaveLocationEvent.Error)
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
        saveLocationJob?.cancel()
        getLocationJob?.cancel()
    }
}

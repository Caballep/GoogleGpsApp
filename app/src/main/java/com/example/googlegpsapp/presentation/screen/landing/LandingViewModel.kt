package com.example.googlegpsapp.presentation.screen.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlegpsapp.domain.usecase.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.googlegpsapp.core.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    private var fetchLocationJob: Job? = null

    private val _locationEvent = MutableStateFlow<LandingEvents>(LandingEvents.Initial)
    val locationEvent: StateFlow<LandingEvents> = _locationEvent.asStateFlow()

    private fun emitLocationEvent(event: LandingEvents) {
        _locationEvent.value = event
    }

    fun fetchLocation() {
        fetchLocationJob?.cancel()
        fetchLocationJob = viewModelScope.launch {
            when(val result = getLocationUseCase.invoke()) {
                is Result.Success -> {
                    emitLocationEvent(LandingEvents.NewDetailedLocationModel(result.data!!))
                }
                is Result.Error -> {
                    emitLocationEvent(LandingEvents.ErrorGettingDetailedLocationModel)
                }
                else -> {}
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchLocationJob?.cancel()
    }
}

package com.example.googlegpsapp.presentation.screen.landing

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*

@Composable
fun LandingScreen(viewModel: LandingViewModel) {
    val locationEvent by viewModel.locationEvent.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.fetchLocation()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "LocationOn"
                )
            }
        }
    ) {
        val scaffoldState = rememberScaffoldState()

        LaunchedEffect(locationEvent) {
            when (locationEvent) {
                is LandingEvents.NewDetailedLocationModel -> {
                    val locationModel = (locationEvent as LandingEvents.NewDetailedLocationModel).detailedLocationModel
                    val message = "Latitude: ${locationModel.latitude}, Longitude: ${locationModel.longitude}"
                    scaffoldState.snackbarHostState.showSnackbar(message)
                }
                is LandingEvents.ErrorGettingDetailedLocationModel -> {
                    scaffoldState.snackbarHostState.showSnackbar("Error!")
                }
                else -> {}
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                SnackbarHost(it) { action ->
                    action.dismiss()
                }
            }
        ) {
            // Rest of your UI components
        }
    }
}
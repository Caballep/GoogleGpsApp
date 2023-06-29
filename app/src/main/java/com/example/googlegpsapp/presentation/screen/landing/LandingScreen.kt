package com.example.googlegpsapp.presentation.screen.landing

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*

@Composable
fun LandingScreen(viewModel: LandingViewModel) {
    val saveLocationEvent by viewModel.saveLocationEvent.collectAsState()
    val locationsState by viewModel.locationsEvent.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "LocationOn"
                )
            }
        }
    ) {

    }
}
package com.example.googlegpsapp.presentation.screen.landing

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.googlegpsapp.domain.model.LocationModel
import com.example.googlegpsapp.presentation.screen.landing.composables.LocationDetails
import com.example.googlegpsapp.presentation.screen.landing.composables.LocationList
import com.example.googlegpsapp.presentation.screen.landing.composables.NewLocation

@Composable
fun LandingScreen(viewModel: LandingViewModel) {
    val saveLocationEvent by viewModel.saveLocationEvent.collectAsState()
    val locationsState by viewModel.locationsEvent.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val showNewLocationDialog = remember { mutableStateOf(false) }
    val showLocationDetailsDialog = remember { mutableStateOf(false) }

    lateinit var locationItemSelected: LocationModel

    when (saveLocationEvent) {
        is SaveLocationEvent.Done -> {
            viewModel.fetchLocation()
        }
        else -> {
            // TODO: Handle all the other state elegantly
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showNewLocationDialog.value = true
                }
            ) {
                Row(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Add Location",
                        modifier = Modifier
                            .size(26.dp)
                            .padding(bottom = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Save my Location",
                        fontSize = 16.sp,
                        modifier = Modifier.widthIn(max = 170.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            with(locationsState) {
                when (this) {
                    is LocationsEvent.Initial -> {
                        viewModel.fetchLocation()
                    }
                    is LocationsEvent.Loading -> {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    is LocationsEvent.NoData -> {
                        Text(text = "Nothing to display.")
                    }
                    is LocationsEvent.Error -> {
                        Text(text = "Something went wrong.")
                    }
                    is LocationsEvent.Data -> {
                        LocationList(locations = this.locations, onLocationClick = { id ->
                            locationItemSelected = this.locations.first { it.id == id }
                            showLocationDetailsDialog.value = true
                        })
                    }
                }
            }

            if (showNewLocationDialog.value) {
                Dialog(
                    onDismissRequest = {
                        showNewLocationDialog.value = false
                    },
                    properties = DialogProperties(),
                ) {
                    NewLocation(onLocationEntered = { name ->
                        viewModel.saveLocation(name)
                        showNewLocationDialog.value = false
                    })
                }
            }

            if (showLocationDetailsDialog.value) {
                Dialog(
                    onDismissRequest = {
                        showLocationDetailsDialog.value = false
                    },
                    properties = DialogProperties(),
                ) {
                    LocationDetails(locationModel = locationItemSelected, onDelete = { id ->

                    })
                }
            }
        }
    }
}

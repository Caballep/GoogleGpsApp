package com.example.googlegpsapp.presentation.screen.landing.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.googlegpsapp.domain.model.LocationModel

@Composable
fun LocationList(locations: List<LocationModel>, onLocationClick: (id: Int) -> Unit) {
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            itemsIndexed(locations) { _, location ->
                LocationListTile(id = location.id, name = location.name, onClick = { id ->
                    onLocationClick(id)
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationListPreview() {

    val locations = listOf(
        LocationModel(1, "Location 1", 12.345, 67.890, "csdcs dwccw"),
        LocationModel(2, "Location 2", 34.567, 98.765, "cw4 3fe f3f3"),
        LocationModel(2, "Location 3", 34.567, 98.765, "ciwu9u4cn94c"),
    )

    LocationList(
        locations = locations, onLocationClick = {})
}
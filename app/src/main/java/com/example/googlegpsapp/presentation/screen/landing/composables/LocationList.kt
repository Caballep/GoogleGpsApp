package com.example.googlegpsapp.presentation.screen.landing.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.googlegpsapp.presentation.screen.landing.data.DetailedLocationModel
import java.util.Date

@Composable
fun LocationList(locations: List<DetailedLocationModel>, onLocationClick: (Int) -> Unit) {
    LazyColumn {
        itemsIndexed(locations) { _, location ->
            LocationListTile(id = location.id, name = location.name, onClick = { id ->
                onLocationClick(id)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationListPreview() {

    val locations = listOf(
        DetailedLocationModel(1, "Location 1", 12.345, 67.890, Date()),
        DetailedLocationModel(2, "Location 2", 34.567, 98.765, Date()),
        DetailedLocationModel(2, "Location 3", 34.567, 98.765, Date()),
    )

    LocationList(
        locations = locations, onLocationClick = {})
}

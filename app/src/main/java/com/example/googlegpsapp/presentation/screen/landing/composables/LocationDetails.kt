package com.example.googlegpsapp.presentation.screen.landing.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googlegpsapp.domain.model.LocationModel

@Composable
fun LocationDetails(locationModel: LocationModel, onDelete: (id: Int) -> Unit) {
    Box(
        modifier = Modifier.background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "Location Details",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("Name", locationModel.name)
            DetailItem("Latitude", locationModel.latitude.toString())
            DetailItem("Longitude", locationModel.longitude.toString())
            DetailItem("Time", locationModel.formattedTime)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = AnnotatedString("Open it in GoogleMaps"),
                    onClick = { offset ->
                        // Handle click event
                    },
                    style = TextStyle(
                        color = Color.Blue,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = { onDelete(locationModel.id) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Delete")
                }
            }
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$label:", fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun LocationDetailsPreview() {
    LocationDetails(
        LocationModel(
            id = 1,
            name = "Meh",
            longitude = 10.43,
            latitude = 23.23,
            formattedTime = "05/23/2033 13:10"
        )
    ) {

    }
}


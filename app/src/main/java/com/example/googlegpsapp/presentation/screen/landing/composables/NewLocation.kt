package com.example.googlegpsapp.presentation.screen.landing.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewLocation(onLocationEntered: (String) -> Unit) {
    val enteredLocation = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Enter a name for your current location:", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            placeholder = { Text(text = "Treasure location", color = Color.Gray) },
            value = enteredLocation.value,
            onValueChange = { enteredLocation.value = it },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onLocationEntered(enteredLocation.value) }
            )
        )
    }
}



@Preview(showBackground = true)
@Composable
fun NewLocationPreview() {
    NewLocation() {

    }
}


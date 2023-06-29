package com.example.googlegpsapp.presentation.other

import android.content.Context
import android.content.Intent
import android.net.Uri

// TODO: Temporal, Think on better way to do this
class IntentHelper(private val context: Context) {
    fun openGoogleMaps(latitude: Double, longitude: Double, locationName: String) {
        val uri = "geo:0,0?q=$latitude,$longitude($locationName)"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // Google Maps app is not installed, handle the situation accordingly
        }
    }
}

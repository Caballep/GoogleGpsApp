package com.example.googlegpsapp.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.googlegpsapp.presentation.PermissionsHelper

open class BaseActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionsHelper = PermissionsHelper(this)
        permissionsHelper.requestPermission(PermissionsHelper.Permission.ACCESS_FINE_LOCATION)
    }
}

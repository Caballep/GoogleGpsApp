package com.example.googlegpsapp.presentation

import android.Manifest.permission as permissions
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionsHelper(
    private val activity: Activity,
    ) {

    fun requestPermission(permission: Permission) {
        if (!isPermissionApproved(permission)) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission.manifestValue),
                permission.ordinal
            )
        }
    }

    private fun isPermissionApproved(permission: Permission): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            activity,
            permissions.ACCESS_FINE_LOCATION
        )
    }

    enum class Permission(val manifestValue: String) {
        ACCESS_FINE_LOCATION(permissions.ACCESS_FINE_LOCATION)
    }
}

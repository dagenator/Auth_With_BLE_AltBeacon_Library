package com.example.beacon_library_test.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.beacon_library_test.MainActivity
import javax.inject.Inject

class PermissionsUtils @Inject constructor() {

    fun checkOrAskPermission(
        activity: Activity,
        permissions: String,
        requestCode: Int,
        callback: () -> Unit
    ) {
        val permission = ContextCompat.checkSelfPermission(activity.baseContext, permissions)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            callback.invoke()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permissions),
                requestCode
            )
        }
    }

    //todo нормален ли такой рефакторинг пермишинов?
    fun requestBackgroundLocation(
        activity: Activity,
        callback: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            checkOrAskPermission(
                activity,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                MainActivity.PERMISSION_REQUEST_BACKGROUND_LOCATION
            ) {
                requestBlePermission(activity, callback)
            }
        } else requestBlePermission(activity, callback)
    }

    fun requestScanPermission(
        activity: Activity,
        callback: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            checkOrAskPermission(
                activity,
                Manifest.permission.BLUETOOTH_SCAN,
                MainActivity.PERMISSION_REQUEST_BLUETOOTH_SCAN
            ) {
                callback.invoke()
            }
        } else callback.invoke()
    }

    fun requestBlePermission(
        activity: Activity,
        callback: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            checkOrAskPermission(
                activity,
                Manifest.permission.BLUETOOTH_CONNECT,
                MainActivity.PERMISSION_REQUEST_BLUETOOTH_CONNECT
            ) {
                requestScanPermission(activity, callback)
            }
        } else requestScanPermission(activity, callback)
    }
}
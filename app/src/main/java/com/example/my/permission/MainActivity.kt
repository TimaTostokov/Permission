package com.example.my.permission

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var isReadPermissionGranted = false
    private var isLocationPermissionGranted = false
    private var isRecordPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                isReadPermissionGranted =
                    permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE]
                        ?: isReadPermissionGranted
                isLocationPermissionGranted =
                    permissions[android.Manifest.permission.ACCESS_FINE_LOCATION]
                        ?: isLocationPermissionGranted
                isRecordPermissionGranted = permissions[android.Manifest.permission.RECORD_AUDIO]
                    ?: isRecordPermissionGranted
            }
        requestPermissions()
    }

    private fun requestPermissions() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) ==
                PackageManager.PERMISSION_GRANTED
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED
        isRecordPermissionGranted =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) ==
                    PackageManager.PERMISSION_GRANTED

        val permissionsRequest: MutableList<String> = ArrayList()
        if (!isReadPermissionGranted) {
            permissionsRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!isLocationPermissionGranted) {
            permissionsRequest.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!isRecordPermissionGranted) {
            permissionsRequest.add(android.Manifest.permission.RECORD_AUDIO)
        }
        if (permissionsRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsRequest.toTypedArray())
        }
    }

}
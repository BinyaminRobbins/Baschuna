package com.syntapps.bashcuna.other

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.location.LocationServices
import com.google.firebase.crashlytics.FirebaseCrashlytics

class UserLocationServices(val mContext: Context, private val activity: Activity): ActivityCompat.OnRequestPermissionsResultCallback {

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            1
        )
    }

    private fun isLocationEnabled(): Boolean {
        var result = false
        val locationManager: LocationManager? =
            mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.let {
            result = it.isProviderEnabled(LocationManager.GPS_PROVIDER) || it.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }
        return result
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                canGetLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun canGetLocation() : Boolean{
        return if (checkPermissions()) {
            if (isLocationEnabled()) {
                true
            } else {
                Toast.makeText(mContext, "Location must be turned ON", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                mContext.startActivity(intent)
                false
            }
        } else {
            requestPermissions()
            false
        }
    }
}
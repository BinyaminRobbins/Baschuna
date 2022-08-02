package com.syntapps.bashcuna.ui.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.syntapps.bashcuna.other.UserLocationServices
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val _userLocation = MutableLiveData<LatLng>()
    val userLocation: LiveData<LatLng> = _userLocation


    @SuppressLint("MissingPermission")
    fun getUserLocation(userLocationServices: UserLocationServices) {
        viewModelScope.launch {
            if (userLocationServices.canGetLocation()) {
                LocationServices.getFusedLocationProviderClient(userLocationServices.mContext).lastLocation.addOnSuccessListener { task ->
                    val latLng = LatLng(task.latitude, task.longitude)
                    _userLocation.value = latLng
                }.addOnFailureListener {
                    Log.i("LocationViewModel", "Failed to get user location")
                    FirebaseCrashlytics.getInstance().log("Failed to get user location")
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
            }
        }
    }


}
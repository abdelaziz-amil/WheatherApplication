package com.android.ubo.androidweatherubo.domain.view.weatherDetailListViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

data class LocationViewModelState(
    var location: Location? = null,
    var isLocationSearching: Boolean = false,
)

class LocationViewModel(private val client: FusedLocationProviderClient? = null, private val context : Context) : ViewModel() {

    companion object {
        private const val UPDATE_INTERVAL_SECS = 10L
        private const val FASTEST_UPDATE_INTERVAL_SECS = 2L
    }

    var askLocationPermission = MutableStateFlow(false)
        private set

    fun launchLocationPermission(request: Boolean) {
        askLocationPermission.value = request
    }

    @SuppressLint("MissingPermission")
    fun locate() = callbackFlow<LocationViewModelState> {
        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(UPDATE_INTERVAL_SECS)
            fastestInterval = TimeUnit.SECONDS.toMillis(FASTEST_UPDATE_INTERVAL_SECS)
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation

                Toast.makeText(
                    context,
                    "Get location $location.",
                    Toast.LENGTH_SHORT
                ).show()

                trySend(LocationViewModelState(location, false))
            }
        }

        trySend(LocationViewModelState(null, true))
        client?.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper())

        awaitClose { client?.removeLocationUpdates(callBack) }
    }
}
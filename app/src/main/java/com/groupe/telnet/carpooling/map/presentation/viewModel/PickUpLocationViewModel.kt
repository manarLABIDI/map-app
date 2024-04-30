package com.groupe.telnet.carpooling.map.presentation.viewModel

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.osmdroid.util.GeoPoint
import java.text.DecimalFormat
import javax.inject.Inject


@HiltViewModel
class PickUpLocationViewModel @Inject constructor() : ViewModel() {


    private val decimalFormat = DecimalFormat("#.#####")
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
    )

    private var _locationInfo = mutableStateOf("")
    val locationInfo: State<String> = _locationInfo

    private val _startPoint = MutableStateFlow<GeoPoint?>(null)
    val startPoint: StateFlow<GeoPoint?> = _startPoint


    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresPermission(
        anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
    )
    fun fetchCurrentLocation(context: Context) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        viewModelScope.launch(Dispatchers.IO) {
                val priority = if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Priority.PRIORITY_HIGH_ACCURACY
                } else {
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY
                }
                val result = locationClient.getCurrentLocation(
                    priority,
                    CancellationTokenSource().token,
                ).await()
                result?.let {
                    _startPoint.value = GeoPoint(it.latitude, it.longitude) // Example of updating a point
                    _locationInfo.value = "${decimalFormat.format(it.latitude)}, ${decimalFormat.format(it.longitude)}"

                }



            }
        }
    }

package com.groupe.telnet.carpooling.map.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.groupe.telnet.carpooling.map.iconButtons.locationIcon
import com.groupe.telnet.carpooling.map.location.permission.PermissionRationaleDialog
import com.groupe.telnet.carpooling.map.location.permission.RationaleState
import com.groupe.telnet.carpooling.map.utils.CustomOutlinedTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.osmdroid.util.GeoPoint
import java.text.DecimalFormat

@SuppressLint("PermissionLaunchedDuringComposition")
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun PickUpLocationField(
    pickLocation : (GeoPoint) -> Unit,
    labelText: String) {

    val context = LocalContext.current
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
    )

    val decimalFormat = DecimalFormat("#.#####")
    val locationPermissionState = rememberMultiplePermissionsState(permissions)
    var rationaleState by remember { mutableStateOf<RationaleState?>(null) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var locationInfo by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    // Only check if location services are enabled once
    val isLocationEnabled = remember {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
    ) {
        CustomOutlinedTextField(
            labelText = labelText,
            value = locationInfo,
            leadingIcon = { locationIcon() },
            onClick = {

                    if (locationPermissionState.allPermissionsGranted) {
                        scope.launch(Dispatchers.IO) {
                            val priority = if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
                                Priority.PRIORITY_HIGH_ACCURACY
                            } else {
                                Priority.PRIORITY_BALANCED_POWER_ACCURACY
                            }
                            val result = locationClient.getCurrentLocation(
                                priority,
                                CancellationTokenSource().token,
                            ).await()
                            result?.let { fetchedLocation ->
                                locationInfo = "${decimalFormat.format(fetchedLocation.latitude)}, ${decimalFormat.format(fetchedLocation.longitude)}"
                                val geoPoint = GeoPoint(fetchedLocation.latitude, fetchedLocation.longitude)
                                pickLocation(geoPoint)
                            }

                        }
                    } else {
                        showPermissionDialog = true
                    }


            },
        )
    }


    if (showPermissionDialog) {
        if (locationPermissionState.shouldShowRationale) {
            rationaleState = RationaleState(
                title = "Request Precise Location",
                rationale = "To use this feature, you need to grant access by accepting the location permission dialog. Would you like to continue?",
                onRationaleReply = { proceed ->
                    if (proceed) {

                        locationPermissionState.launchMultiplePermissionRequest()
                    }
                    rationaleState = null
                    showPermissionDialog = false
                },
            )
        } else {
            locationPermissionState.launchMultiplePermissionRequest()
            showPermissionDialog = false
        }
    }

    rationaleState?.let { PermissionRationaleDialog(it) }
}


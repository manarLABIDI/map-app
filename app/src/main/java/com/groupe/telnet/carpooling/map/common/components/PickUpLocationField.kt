package com.groupe.telnet.carpooling.map.common.components

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.groupe.telnet.carpooling.map.common.iconButtons.locationIcon
import com.groupe.telnet.carpooling.map.common.permission.PermissionRationaleDialog
import com.groupe.telnet.carpooling.map.common.permission.RationaleState
import com.groupe.telnet.carpooling.map.presentation.viewModel.PickUpLocationViewModel
import com.groupe.telnet.carpooling.map.utils.CustomOutlinedTextField
@SuppressLint("PermissionLaunchedDuringComposition")
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PickUpLocationField(
    labelText: String,
    pickUpLocationViewModel: PickUpLocationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val locationPermissionState = rememberMultiplePermissionsState(permissions)
    var rationaleState by remember { mutableStateOf<RationaleState?>(null) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    val locationInfo by pickUpLocationViewModel.locationInfo

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

                    pickUpLocationViewModel.fetchCurrentLocation(context)


                } else {
                    showPermissionDialog = true
                }


            }
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


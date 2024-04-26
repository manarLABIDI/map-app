package com.groupe.telnet.carpooling.map.common.permission

import android.Manifest
import android.content.Intent
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen() {
    val context = LocalContext.current
    val fineLocationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )
    var rationaleState by remember {
        mutableStateOf<RationaleState?>(null)
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Show rationale dialog when needed
            rationaleState?.run { PermissionRationaleDialog(rationaleState = this) }



            PermissionRequestButton(
                isGranted = fineLocationPermissionState.allPermissionsGranted,
                title = "Precise location access",
            ) {
                if (fineLocationPermissionState.shouldShowRationale) {
                    rationaleState = RationaleState(
                        "Request Precise Location",
                        "In order to use this feature please grant access by accepting " + "the location permission dialog." + "\n\nWould you like to continue?",
                    ) { proceed ->
                        if (proceed) {
                            fineLocationPermissionState.launchMultiplePermissionRequest()
                        }
                        rationaleState = null

                    }
                } else {
                    fineLocationPermissionState.launchMultiplePermissionRequest()
                }
            }

        }
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = { context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS)) },
        ) {
            Icon(Icons.Outlined.Settings, "Location Settings")
        }
    }
}


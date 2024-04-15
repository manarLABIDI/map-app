package com.groupe.telnet.carpooling.map.components
//
//import android.Manifest
//import android.content.Context
//import android.content.IntentSender
//import android.view.Surface
//import android.widget.Button
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.IntentSenderRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberMultiplePermissionsState
//import com.google.android.gms.common.api.ResolvableApiException
//import com.google.android.gms.location.*
//import com.google.android.gms.tasks.Task
//import com.groupe.telnet.carpooling.map.utils.LoggerUtil
//
//
//@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun LocationPermissionWrapper(
//    content: @Composable () -> Unit
//) {
//    val logger  = LoggerUtil(c = "LocationPermissionWrapper")
//    val context = LocalContext.current
//
//    var isLocationEnabled by rememberSaveable{ mutableStateOf(true) }
//    val dialogState = remember { mutableStateOf(false) }
//
//    val locationPermissionsState = rememberMultiplePermissionsState(
//        permissions = listOf(
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//    )
//    val settingResultRequest = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartIntentSenderForResult()
//    ) { activityResult ->
//        if (activityResult.resultCode == 0){
//            logger.info("Denied : ${activityResult.resultCode}")
//            isLocationEnabled = false
//        } else {
//            logger.info("Accepted : ${activityResult.resultCode}")
//            isLocationEnabled = true
//        }
//    }
//
//    LaunchedEffect(key1 = Unit){
//        if(!locationPermissionsState.allPermissionsGranted){
//            // Request grant Location Permission
//            locationPermissionsState.launchMultiplePermissionRequest()
//        }else{
//            // Enable location
//            checkLocationSetting(
//                context = context,
//                onEnabled = {
//                    logger.info("Location Enabled")
//                    isLocationEnabled = true
//                },
//                onDisabled = {
//                    logger.info("Location Disabled")
//                    isLocationEnabled = false
//                    settingResultRequest.launch(it)
//                }
//            )
//        }
//    }
//
//    if(locationPermissionsState.allPermissionsGranted && isLocationEnabled){
//        content()
//    }else {
//        val allPermissionsRevoked =
//            locationPermissionsState.permissions.size == locationPermissionsState.revokedPermissions.size
//
//        val textToShow =  if(locationPermissionsState.allPermissionsGranted){
//            "To proceed, please enable GPS (Location Services) on your device."
//        } else if (!allPermissionsRevoked) {
//            // If not all the permissions are revoked, it's because the user accepted the COARSE
//            // location permission, but not the FINE one.
//            "Thanks for letting access your approximate location. Please grant us fine location."
//        } else if (locationPermissionsState.shouldShowRationale) {
//            // Both location permissions have been denied
//            "Getting your exact location is important for this app. Please grant us location permission."
//        } else {
//            // First time the user sees this feature or the user doesn't want to be asked again
//            "This application requires location permission"
//        }
//
//        val btnText = if(locationPermissionsState.allPermissionsGranted){
//            "Turn On Location"
//        }else{
//            "Request Access"
//        }
//
//        // Replace AlertDialog with Dialog
//        if (dialogState.value) {
//            Dialog(
//                onDismissRequest = { dialogState.value = false },
//                properties = DialogProperties(usePlatformDefaultWidth = false)
//            ) {
//                Surface(
//                    modifier = Modifier
//                        .wrapContentSize()
//                        .padding(16.dp),
//                    shape = MaterialTheme.shapes.medium
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .padding(16.dp)
//                            .fillMaxWidth()
//                    ) {
//                        Text(
//                            text = "To proceed, please enable GPS (Location Services) on your device.",
//                            style = MaterialTheme.typography.bodySmall,
//                            modifier = Modifier.padding(bottom = 16.dp)
//                        )
//                        Button(
//                            onClick = {
//                                dialogState.value = false
//                                checkLocationSetting(
//                                    context = context,
//                                    onEnabled = {
//                                        isLocationEnabled = true
//                                    },
//                                    onDisabled = {
//                                        isLocationEnabled = false
//                                    }
//                                )
//                            }
//                        ) {
//                            Text(text = "Turn On GPS")
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//}
//
//
//
//// call this function on button click  // Enable gps
//private fun checkLocationSetting(
//    context: Context,
//    onDisabled: (IntentSenderRequest) -> Unit,
//    onEnabled: () -> Unit
//) {
//   // val logger  = LoggerUtil()
//    val locationRequest = LocationRequest.Builder(1000)
//        .setMinUpdateIntervalMillis(1000)
//        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//        .build()
//
//    val client: SettingsClient = LocationServices.getSettingsClient(context)
//    val builder: LocationSettingsRequest.Builder = LocationSettingsRequest
//        .Builder()
//        .addLocationRequest(locationRequest)
//
//    val gpsSettingTask: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
//
//
//    gpsSettingTask.addOnSuccessListener { onEnabled() }
//    gpsSettingTask.addOnFailureListener { exception ->
//        if (exception is ResolvableApiException) {
//            try {
//                val intentSenderRequest = IntentSenderRequest
//                    .Builder(exception.resolution)
//                    .build()
//                onDisabled(intentSenderRequest)
//            } catch (sendEx: IntentSender.SendIntentException) {
//                //logger.error(sendEx,"checkLocationSetting")
//            }
//        }
//    }
//
//}
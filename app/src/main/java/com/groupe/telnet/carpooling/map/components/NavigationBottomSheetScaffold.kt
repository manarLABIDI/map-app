package com.groupe.telnet.carpooling.map.components

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.MapView
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavigationBottomSheetScaffold(
    pinnedLocation: State<GeoPoint?>,
    selectedLocation: State<GeoPoint?>,
    mapView: MapView,
    map: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    val startPoint = remember { mutableStateOf<GeoPoint?>(null) }
    val endPoint = remember { mutableStateOf<GeoPoint?>(null) }
    var shouldDrawPath by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        modifier =
        Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                scope.launch {
                    if (scaffoldState.bottomSheetState.hasExpandedState) {
                        scaffoldState.bottomSheetState.hide()
                    }
                }
            })
        },
        sheetContainerColor = Color.White,
        sheetContent = {


            Column(
                modifier = Modifier
                    .height(350.dp)
                    .padding(16.dp),


                ) {


                DateTime()
                Spacer(modifier = Modifier.height(10.dp))
                PickUpLocationField(
                    pickLocation = { pickPoint ->
                        startPoint.value = pickPoint
                    },
                    "Pick up location"
                )

                Spacer(modifier = Modifier.height(10.dp))
                DestinationLocationField(
                    dropLocation = { dropPoint ->
                        endPoint.value = dropPoint
                    },
                    "Drop off destination",
                    selectedLocation,
                    pinnedLocation
                )
                Spacer(modifier = Modifier.height(10.dp))
                buttonComponent {
                    shouldDrawPath = true
                }
            }
        },
        sheetPeekHeight = LocalConfiguration.current.screenHeightDp.dp * 0.15f,
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        content = map,

      //  sheetPeekHeight = 100.dp
    )

//    LaunchedEffect(endPoint.value) {
//        if (startPoint.value != null && endPoint.value != null) {
//            mapView.overlays.removeAll { it is Polyline }
//            shouldDrawPath = true
//        }
//    }
    if (shouldDrawPath) {
        if (startPoint.value != null && endPoint.value != null) {
            mapView.overlays.removeAll { it is Polyline }
            drawPath(mapView, startPoint.value!!, endPoint.value!!)
        }

    }
}
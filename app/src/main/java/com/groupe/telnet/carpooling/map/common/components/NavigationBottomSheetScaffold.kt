package com.groupe.telnet.carpooling.map.common.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.presentation.view.LocationSearchBar
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavigationBottomSheetScaffold(
    searchedLocation: (GeoPoint) -> Unit,
    pinnedLocation: State<GeoPoint?>,
    mapView: MapView,
    map: @Composable (PaddingValues) -> Unit
) {


    val selectedLocation = remember { mutableStateOf<GeoPoint?>(null) }
    var showSearchBar by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val startPoint = remember { mutableStateOf<GeoPoint?>(null) }
    val endPoint = remember { mutableStateOf<GeoPoint?>(null) }
    var shouldDrawPath by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    if (showSearchBar) {
        LocationSearchBar(
            onLocation = { geoPoint ->
                selectedLocation.value = geoPoint
            }
        )
    }
    LaunchedEffect(selectedLocation.value) {
        if (selectedLocation.value != null) {
            searchedLocation(selectedLocation.value!!)
        }
    }

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
                    pinnedLocation,
                    onShowSearchBar = { show ->
                        showSearchBar = show

                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                ValidationButton {
                    showSearchBar = false
                    shouldDrawPath = true

                }
            }
        },

        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        content = map,
        sheetPeekHeight = 100.dp
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



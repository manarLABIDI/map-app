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
import androidx.hilt.navigation.compose.hiltViewModel
import com.groupe.telnet.carpooling.map.presentation.viewModel.LocationSearchViewModel
import com.groupe.telnet.carpooling.map.presentation.viewModel.RoadPathViewModel
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavigationBottomSheetScaffold(
    searchedLocation: (GeoPoint) -> Unit,
    pinnedLocation: State<GeoPoint?>,
    map: @Composable (PaddingValues) -> Unit
) {
    val roadPathViewModel: RoadPathViewModel = hiltViewModel()
    val locationSearchViewModel: LocationSearchViewModel = hiltViewModel()

    val selectedLocation = remember { mutableStateOf<GeoPoint?>(null) }
    val scope = rememberCoroutineScope()
    val startPoint = remember { mutableStateOf<GeoPoint?>(null) }
    val endPoint = remember { mutableStateOf<GeoPoint?>(null) }
    val isSearchBarVisible by locationSearchViewModel.isSearchBarVisible.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()

    if (isSearchBarVisible) {
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
                    .height(300.dp)
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
                ValidationButton {
                    locationSearchViewModel.hideSearchBar()
                    if (startPoint.value != null && endPoint.value != null) {
                        roadPathViewModel.drawPath(startPoint.value!!, endPoint.value!!)
                    }


                }
            }
        },

        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        content = map
    )


}



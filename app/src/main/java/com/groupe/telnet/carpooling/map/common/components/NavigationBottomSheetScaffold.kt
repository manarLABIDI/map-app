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
import com.groupe.telnet.carpooling.map.presentation.viewModel.DestinationLocationViewModel
import com.groupe.telnet.carpooling.map.presentation.viewModel.LocationSearchViewModel
import com.groupe.telnet.carpooling.map.presentation.viewModel.PickUpLocationViewModel
import com.groupe.telnet.carpooling.map.presentation.viewModel.RoadPathViewModel
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavigationBottomSheetScaffold(
    map: @Composable (PaddingValues) -> Unit
) {
    val roadPathViewModel: RoadPathViewModel = hiltViewModel()
    val locationSearchViewModel: LocationSearchViewModel = hiltViewModel()
    val pickUpLocationViewModel: PickUpLocationViewModel = hiltViewModel()
    val destinationLocationViewModel : DestinationLocationViewModel = hiltViewModel()

    val startPoint by pickUpLocationViewModel.startPoint.collectAsState()
    val endPoint by destinationLocationViewModel.endPoint.collectAsState()
    val isSearchBarVisible by locationSearchViewModel.isSearchBarVisible.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()

    if (isSearchBarVisible) {
        LocationSearchBar()
    }

    BottomSheetScaffold(

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
                    "Pick up location"
                )

                Spacer(modifier = Modifier.height(10.dp))
                DestinationLocationField(
                    "Drop off destination")
                Spacer(modifier = Modifier.height(10.dp))
                ValidationButton {
                     locationSearchViewModel.hideSearchBar()
//                    if (startPoint != null && endPoint != null) {
//                        roadPathViewModel.drawPath(startPoint!!, endPoint!!)
//                    }


                }
            }
        },

        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        content = map
    )


}



package com.groupe.telnet.carpooling.map.view

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.groupe.telnet.carpooling.map.MapView
import com.groupe.telnet.carpooling.map.components.NavigationBottomSheetScaffold
import com.groupe.telnet.carpooling.map.searchLocation.LocationSearchBar
import org.osmdroid.util.GeoPoint

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            val context = LocalContext.current
            val mapView = remember { org.osmdroid.views.MapView(context) }

            val selectedLocation = remember { mutableStateOf<GeoPoint?>(null) }
            val pinnedLocation = remember { mutableStateOf<GeoPoint?>(null) }

            LocationSearchBar(
                onLocation = { geoPoint ->
                    selectedLocation.value = geoPoint
                }
            )
            NavigationBottomSheetScaffold(pinnedLocation, selectedLocation, mapView) { paddingValues ->
                Box(Modifier.padding(paddingValues)) {
                    MapView(mapView, pinLocation = { pinPoint ->
                        pinnedLocation.value = pinPoint
                    }, selectedLocation)
                }
            }
        }
    }
}
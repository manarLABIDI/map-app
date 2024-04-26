package com.groupe.telnet.carpooling.map.presentation.view


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.groupe.telnet.carpooling.map.common.components.NavigationBottomSheetScaffold
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
            NavigationBottomSheetScaffold(searchedLocation = { searchedLocation ->
                selectedLocation.value = searchedLocation


            }, pinnedLocation) { paddingValues ->
                Box(Modifier.padding(paddingValues)) {
                    MapView(mapView, pinLocation = { pinPoint ->
                        pinnedLocation.value = pinPoint
                    }, selectedLocation)
                }
            }
        }
    }
}

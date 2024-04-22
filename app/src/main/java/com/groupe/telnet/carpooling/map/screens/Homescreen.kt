package com.groupe.telnet.carpooling.map.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.groupe.telnet.carpooling.map.MapView
import com.groupe.telnet.carpooling.map.components.LocationSearchBar
import com.groupe.telnet.carpooling.map.components.NavigationBottomSheetScaffold

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box {

            LocationSearchBar()
            NavigationBottomSheetScaffold {
                MapView()
            }
        }
    }
}

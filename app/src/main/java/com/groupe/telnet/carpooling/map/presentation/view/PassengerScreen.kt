package com.groupe.telnet.carpooling.map.presentation.view


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.groupe.telnet.carpooling.map.common.components.MapComposable
import com.groupe.telnet.carpooling.map.common.components.NavigationBottomSheetScaffold


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PassengerScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box {


            NavigationBottomSheetScaffold() { paddingValues ->
                Box(Modifier.padding(paddingValues)) {
                    MapComposable()
                }
            }
        }
    }
}

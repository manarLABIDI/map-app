package com.groupe.telnet.carpooling.map.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavigationBottomSheetScaffold(
    map: @Composable (PaddingValues) -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        containerColor = Color.White,
        sheetContent = {



            Column(
                modifier = Modifier
                    .height(350.dp)
                    .padding(16.dp),


                ) {


                DateTime()
                Spacer(modifier = Modifier.height(10.dp))
                PickUpLocationField("Pick up location")

                Spacer(modifier = Modifier.height(10.dp))
                DestinationLocationField("Drop off destination")
                Spacer(modifier = Modifier.height(10.dp))
                buttonComponent()

            }




        },
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        content =map,
        sheetPeekHeight = 100.dp
    )
}
package com.groupe.telnet.carpooling.map.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavigationBottomSheetScaffold(
    content: @Composable (PaddingValues) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        containerColor = Color.White,
        sheetContent = {


            Column(
                modifier = Modifier
                    .height(200.dp)
                    .padding(16.dp),

                verticalArrangement = Arrangement.Center,
            ) {

                datetime()
                Spacer(modifier = Modifier.height(16.dp))
                pickUpLocationButton()
            }

                Box(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(16.dp)

                ) {
                    buttonComponent()
                }



        },
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        content = content
    )
}

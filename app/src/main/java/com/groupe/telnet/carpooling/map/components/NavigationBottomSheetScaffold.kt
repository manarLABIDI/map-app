package com.groupe.telnet.carpooling.map.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter



@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavigationBottomSheetScaffold(
    content: @Composable (PaddingValues) -> Unit
    /*onLocationClicked: ((Double, Double) -> Unit)? = null*/
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        containerColor = Color.White,
        sheetContent = {


            Column(
                modifier = Modifier
                    .height(280.dp)
                    .padding(16.dp),


            ) {

                datetime()
                Spacer(modifier = Modifier.height(16.dp))
                locationFiled("Pick up location ")
                Spacer(modifier = Modifier.height(16.dp))
                locationFiled("Drop off destination")
            }

                Box(
                    modifier = Modifier
                        .height(80.dp)
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

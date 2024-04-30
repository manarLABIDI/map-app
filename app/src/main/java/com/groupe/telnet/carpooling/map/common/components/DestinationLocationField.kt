package com.groupe.telnet.carpooling.map.common.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.groupe.telnet.carpooling.map.common.iconButtons.locationIcon
import com.groupe.telnet.carpooling.map.presentation.viewModel.DestinationLocationViewModel
import com.groupe.telnet.carpooling.map.presentation.viewModel.LocationSearchViewModel
import com.groupe.telnet.carpooling.map.presentation.viewModel.MapViewModel
import com.groupe.telnet.carpooling.map.utils.CustomOutlinedTextField

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DestinationLocationField(
    labelText: String,
    destinationLocationViewModel :  DestinationLocationViewModel = hiltViewModel()

) {

    val locationSearchViewModel: LocationSearchViewModel = hiltViewModel()
    val destinationLocation by destinationLocationViewModel.locationInfo


    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
    ) {
        CustomOutlinedTextField(
            labelText = labelText,
            value = destinationLocation,
            leadingIcon = { locationIcon() },
            onClick = {

                locationSearchViewModel.showSearchBar()
            },
        )
    }


}
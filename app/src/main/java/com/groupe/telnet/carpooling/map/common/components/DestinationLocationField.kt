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
import com.groupe.telnet.carpooling.map.presentation.viewModel.LocationSearchViewModel
import com.groupe.telnet.carpooling.map.presentation.viewModel.LocationViewModel
import com.groupe.telnet.carpooling.map.utils.CustomOutlinedTextField
import org.osmdroid.util.GeoPoint
import java.text.DecimalFormat

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DestinationLocationField(
    dropLocation : (GeoPoint) -> Unit,
    labelText: String,
    pinnedLocation : State<GeoPoint?>,
    selectedLocation: State<GeoPoint?>

) {
    val locationSearchViewModel: LocationSearchViewModel = hiltViewModel()
    val decimalFormat = DecimalFormat("#.#####")
    var locationInfo by remember { mutableStateOf("") }

    LaunchedEffect(selectedLocation.value) {
        val location = selectedLocation.value
        if (location != null) {
            locationInfo = "${decimalFormat.format(location.latitude)}  ${decimalFormat.format(location.longitude)}"
            val geoPoint = GeoPoint(location.latitude, location.longitude)
            dropLocation(geoPoint)

        }
    }
    LaunchedEffect(pinnedLocation.value) {
        val location = pinnedLocation.value
        if (location != null) {
            locationInfo = "${decimalFormat.format(location.latitude)}, ${decimalFormat.format(location.longitude)}"
            val geoPoint = GeoPoint(location.latitude, location.longitude)
            dropLocation(geoPoint)

        }
    }
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
    ) {
        CustomOutlinedTextField(
            labelText = labelText,
            value = locationInfo,
            leadingIcon = { locationIcon() },
            onClick = {

                locationSearchViewModel.showSearchBar()
            },
        )
    }


}
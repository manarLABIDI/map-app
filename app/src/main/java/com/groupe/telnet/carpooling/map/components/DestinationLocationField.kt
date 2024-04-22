package com.groupe.telnet.carpooling.map.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.util.query
import com.groupe.telnet.carpooling.map.iconButtons.locationIcon
import com.groupe.telnet.carpooling.map.utils.CustomOutlinedTextField

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DestinationLocationField(labelText: String) {

    var locationInfo by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var active by remember { mutableStateOf(false) }
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
    ) {
        CustomOutlinedTextField(
            labelText = labelText,
            value = locationInfo,
            leadingIcon=  { locationIcon() },
            onClick = {
                active = true
            },
        )
    }

}
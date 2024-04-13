package com.groupe.telnet.carpooling.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.ui.theme.BackgroundColor
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pickUpLocationButton() {
    var textFieldText by remember { mutableStateOf("") }

    //Text(text = "Pick up location", modifier = Modifier.padding(bottom = 16.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = textFieldText,
            onValueChange = { textFieldText = it },
            modifier = Modifier.weight(1f)
                .background(color = BackgroundColor),
            label = { Text("Search destination") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        Spacer(modifier = Modifier.padding(20.dp))
        locationButton(onClick = {

        })
    }

}

@Composable
fun locationButton(onClick: () -> Unit) {
    FloatingActionButton(
        containerColor = SkyBlueColor,
        onClick = { onClick() },
        modifier = Modifier.size(48.dp),
    ) {
        Icon(
            tint = Color.White,
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location Icon"
        )
    }
}
package com.groupe.telnet.carpooling.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.ui.theme.BackgroundColor
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun locationFiled(labelText: String){
    var textFieldText by remember { mutableStateOf("") }



    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = textFieldText,
            onValueChange = {},
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp)
                .background(color = BackgroundColor),
            label = { Text(labelText) },
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,

                )
        )
        Spacer(modifier = Modifier.padding(20.dp))

        locationButton(onClick = {})
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



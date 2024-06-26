package com.groupe.telnet.carpooling.map.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor

@Composable
fun buttonComponent() {

    Button(
        onClick = {
            /* TODO()*/
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            SkyBlueColor,
            contentColor = Color.White
        )
    ) {
        Text(
            text = "Find a ride",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }
}


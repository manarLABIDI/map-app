package com.groupe.telnet.carpooling.map.iconButtons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor


@Composable
fun locationIcon(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.LocationOn,
        tint = SkyBlueColor,
        contentDescription = "Location Icon",
        modifier = Modifier
            .size(48.dp)
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick)
    )
}
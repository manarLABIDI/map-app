package com.groupe.telnet.carpooling.map.iconButtons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.R
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import org.osmdroid.views.overlay.Overlay


@Composable
fun locationIcon() {
    Icon(
        imageVector = Icons.Default.LocationOn,
        tint = SkyBlueColor,
        contentDescription = "Location Icon",
        modifier = Modifier
            .size(48.dp)
            .padding(horizontal = 8.dp)
    )
}


@Composable
fun mylocationIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.my_location_24),
        tint = SkyBlueColor,
        contentDescription = "Location Icon",
        modifier = modifier
            .size(72.dp)
            .padding(16.dp)
    )
}

@Composable
fun floatingLocatikkonIcon(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.my_location_24),
            tint = SkyBlueColor,
            contentDescription = "Location Icon",

        )
    }
}

@Composable
fun floatingLocationIcon(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.my_location_24),
            tint = SkyBlueColor,
            contentDescription = "Location Icon",

            )
    }
}
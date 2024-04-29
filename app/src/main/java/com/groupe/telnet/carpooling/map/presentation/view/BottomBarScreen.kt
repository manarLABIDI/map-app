package com.groupe.telnet.carpooling.map.presentation.view

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.R
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: @Composable () -> Unit
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.home), // Custom drawable
                contentDescription = "Home Icon",
                tint = SkyBlueColor,
                modifier = Modifier.size(33.dp)
            )
        }
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.user), // Custom drawable
                contentDescription = "Profile Icon",
                tint = SkyBlueColor,
                modifier = Modifier.size(30.dp)
            )
        }
    )

    object Settings : BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "settings Icon",
                tint = SkyBlueColor,
                modifier = Modifier.size(30.dp)
            )
        }
    )
}

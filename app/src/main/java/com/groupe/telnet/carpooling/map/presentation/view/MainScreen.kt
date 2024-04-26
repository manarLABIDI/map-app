package com.groupe.telnet.carpooling.map.presentation.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.groupe.telnet.carpooling.map.BottomNavGraph
import com.groupe.telnet.carpooling.map.ui.theme.BackgroundColor
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(
            navController = navController)
        }
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Profile,
        BottomBarScreen.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = BackgroundColor
    ) {
        screens.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true
            AddItem(
                screen = screen,
                isSelected = isSelected,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    isSelected: Boolean, // New parameter to track if this item is selected
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(
                text = screen.title,
                color =  if (isSelected) SkyBlueColor else Color.Gray
            )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint = if (isSelected) SkyBlueColor else Color.Gray
            )
        },
        selected = isSelected,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}
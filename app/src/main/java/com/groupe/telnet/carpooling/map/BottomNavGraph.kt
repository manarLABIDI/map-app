package com.groupe.telnet.carpooling.map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.groupe.telnet.carpooling.map.screens.BottomBarScreen
import com.groupe.telnet.carpooling.map.screens.HomeScreen
import com.groupe.telnet.carpooling.map.screens.ProfileScreen
import com.groupe.telnet.carpooling.map.screens.SettingsScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}
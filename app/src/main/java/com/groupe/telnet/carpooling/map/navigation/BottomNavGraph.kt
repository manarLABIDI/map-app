package com.groupe.telnet.carpooling.map.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.groupe.telnet.carpooling.map.presentation.view.*

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomNavGraph(navController: NavHostController, bottomBarState: MutableState<Boolean>) {
    NavHost(
        navController = navController,
        startDestination = "landingPage"
    ) {
        composable(route = BottomBarScreen.Home.route) {
            bottomBarState.value = true
            LandingScreen(
                navigateToPassenger = { navController.navigate("PassengerView") }
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            bottomBarState.value = true
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            bottomBarState.value = true
            SettingsScreen()
        }
        composable("PassengerView") {
            bottomBarState.value = false
            PassengerScreen()
        }
        composable("landingPage") {
            bottomBarState.value = true
            LandingScreen(
                navigateToPassenger = { navController.navigate("PassengerView") }
            )
        }
    }
}
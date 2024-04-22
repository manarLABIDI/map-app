package com.groupe.telnet.carpooling.map


import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.groupe.telnet.carpooling.map.screens.HomeScreen
import com.groupe.telnet.carpooling.map.screens.MainScreen
import com.groupe.telnet.carpooling.map.ui.theme.MapTheme
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, getSharedPreferences("OSM", Context.MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = BuildConfig.BUILD_TYPE
        setContent {
            MapTheme {

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                  //HomeScreen()
                    MainScreen()

                }
            }

        }
    }
}








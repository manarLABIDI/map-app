package com.groupe.telnet.carpooling.map


import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.groupe.telnet.carpooling.map.view.MainScreen
import com.groupe.telnet.carpooling.map.ui.theme.MapTheme
import com.groupe.telnet.carpooling.map.view.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig

@AndroidEntryPoint
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








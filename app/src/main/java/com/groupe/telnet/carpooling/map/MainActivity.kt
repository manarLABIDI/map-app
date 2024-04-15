package com.groupe.telnet.carpooling.map



import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.groupe.telnet.carpooling.map.components.LocationSearchBar
import com.groupe.telnet.carpooling.map.components.MapView
import com.groupe.telnet.carpooling.map.components.NavigationBottomSheetScaffold
import com.groupe.telnet.carpooling.map.ui.theme.MapTheme
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.views.MapView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, getSharedPreferences("OSM", Context.MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = BuildConfig.BUILD_TYPE
        setContent {
            MapTheme {

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavigationScreen()
                }
            }
        }
    }
}

@Composable
fun NavigationScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            LocationSearchBar()
            NavigationBottomSheetScaffold {
                MapView()
            }
        }
    }
}





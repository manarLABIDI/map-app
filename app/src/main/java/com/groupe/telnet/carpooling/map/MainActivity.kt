package com.groupe.telnet.carpooling.map

import com.groupe.telnet.carpooling.map.ui.theme.CarpoolingappTheme
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.groupe.telnet.carpooling.map.components.LocationSearchBar
import com.groupe.telnet.carpooling.map.components.NavigationBottomSheetScaffold
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.api.IGeoPoint
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, getSharedPreferences("OSM", Context.MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = BuildConfig.BUILD_TYPE
        setContent {
            CarpoolingappTheme {

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
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            LocationSearchBar()
            NavigationBottomSheetScaffold {
                OSMMapView()
            }
        }
    }
}

@Composable
fun OSMMapView() {
    var mapCenter: IGeoPoint by rememberSaveable { mutableStateOf(GeoPoint(36.0, 10.25)) }
    var zoom by rememberSaveable { mutableStateOf(9.0) }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                setUseDataConnection(true)
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

                val roadManager = OSRMRoadManager(context, Configuration.getInstance().userAgentValue)
                CoroutineScope(Dispatchers.IO).launch {
                    val roads = roadManager.getRoads(
                        arrayListOf(
                            GeoPoint(36.84924, 10.19023),
                            GeoPoint(36.8586, 10.3),
                        )
                    )
                    withContext(Dispatchers.Main) {
                        roads.forEach { road ->
                            val roadOverlay = RoadManager.buildRoadOverlay(road)
                            overlays.add(roadOverlay)
                        }
                    }
                }


                val gpsMyLocationProvider = GpsMyLocationProvider(context)
                val locationOverlay = MyLocationNewOverlay(gpsMyLocationProvider, this)
                locationOverlay.enableMyLocation()
                locationOverlay.enableFollowLocation()
            }
        },
        update = { view ->
            view.controller.setCenter(mapCenter)
            view.controller.setZoom(zoom)
        }
    )
}




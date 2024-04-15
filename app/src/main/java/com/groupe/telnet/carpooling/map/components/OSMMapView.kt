package com.groupe.telnet.carpooling.map.components

import android.location.Geocoder
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.groupe.telnet.carpooling.map.R
import kotlinx.coroutines.*
import org.osmdroid.api.IGeoPoint
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.*


@Composable
fun OSMMapView() {
    val context = LocalContext.current
    var mapCenter: IGeoPoint by rememberSaveable { mutableStateOf(GeoPoint(36.84, 10.25)) }
    var zoom by rememberSaveable { mutableStateOf(10.0) }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                setUseDataConnection(true)
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)


                val marker = Marker(this)
                marker.position = mapCenter as GeoPoint?
                marker.title = "Marker Title"
                marker.icon = ContextCompat.getDrawable(context, R.drawable.ic_location)
                marker.snippet = "Marker Comment"
                overlays.add(marker)

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
            view.minZoomLevel = 10.0
            view.maxZoomLevel = 40.0
            view.tilesScaleFactor = 1f
            view.overlays.add(
                MapEventsOverlay(
                    object : MapEventsReceiver {
                        override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                            val geocoder = Geocoder(context, Locale.getDefault())
                            val lat = p?.latitude ?: return false
                            val lng = p.longitude
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                geocoder.getFromLocation(
                                    lat, lng, 1
                                ) { addresses ->
                                    Toast.makeText(
                                        context,
                                        addresses.getOrNull(0).toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                @Suppress("DEPRECATION")
                                val addresses = geocoder.getFromLocation(lat, lng, 1)
                                Toast.makeText(
                                    context,
                                    addresses?.getOrNull(0).toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            return true
                        }

                        override fun longPressHelper(p: GeoPoint?): Boolean {
                            Toast.makeText(
                                context,
                                "${p?.latitude} ${p?.longitude}",
                                Toast.LENGTH_LONG
                            ).show()
                            return true
                        }

                    }
                )
            )
        }

    )
}

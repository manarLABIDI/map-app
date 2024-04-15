package com.groupe.telnet.carpooling.map.components

import android.location.Geocoder
import android.os.Build
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.preference.PreferenceManager
import com.groupe.telnet.carpooling.map.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.*

@Composable
fun MapView() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val mapView = remember {
        org.osmdroid.views.MapView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }
    }
    val tappedLocation = remember { mutableStateOf("") }

    val startMarker = Marker(mapView).apply {
        icon = ContextCompat.getDrawable(context, R.drawable.ic_location)
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        title = "Marker Title"
        snippet = "Marker Comment"
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    val mLocationOverlay =
                        MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
                    mLocationOverlay.enableMyLocation()
                    mapView.overlays.add(mLocationOverlay)



                    Configuration.getInstance()
                        .load(context, PreferenceManager.getDefaultSharedPreferences(context))
                    mapView.setTileSource(TileSourceFactory.MAPNIK);
                    mapView.setMultiTouchControls(true)

                    val controller = mapView.controller
                    controller.setCenter(
                        GeoPoint(36.84924, 10.19023)
                    )
                    controller.setZoom(16.0)
                    mapView.minZoomLevel = 10.0
                    mapView.maxZoomLevel = 40.0
                    mapView.tilesScaleFactor = 1f

                    mapView.overlays.add(
                        MapEventsOverlay(
                            object : MapEventsReceiver {
                                override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {

                                    tappedLocation.value = "Latitude: ${p?.latitude}, Longitude: ${p?.longitude}"

                                    val lat = p?.latitude ?: return false
                                    val lng = p.longitude
                                    Toast.makeText(
                                        context,
                                        "$lat $lng",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    startMarker.position = p
                                    mapView.overlays.add(startMarker)

                                    return true
                                }

                                override fun longPressHelper(p: GeoPoint?): Boolean {
                                    startMarker.position = p
                                    mapView.overlays.add(startMarker)
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

                    mapView.onResume()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    mapView.onPause()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(factory = { mapView })
}

package com.groupe.telnet.carpooling.map.components

import android.annotation.SuppressLint
import android.graphics.Rect
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.preference.PreferenceManager
import com.groupe.telnet.carpooling.map.R
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.osmdroid.api.IGeoPoint
import org.osmdroid.bonuspack.location.NominatimPOIProvider
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.*


@Composable
fun MapView() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }
    }
    val tappedLocation = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()


    var mapCenter: IGeoPoint by rememberSaveable { mutableStateOf(GeoPoint(36.84924, 10.19023)) }
    val startMarker = remember {
        Marker(mapView).apply {
            icon = ContextCompat.getDrawable(context, R.drawable.ic_location)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = "Marker Title"
            snippet = "Marker Comment"
        }
    }

    val mLocationOverlay = remember {
        MyLocationNewOverlay(GpsMyLocationProvider(context), mapView).apply {
            enableMyLocation()
        }
    }


    val mapEventsOverlay = remember {
        MapEventsOverlay(
            object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                    p?.let {
                        tappedLocation.value = "Latitude: ${p.latitude}, Longitude: ${p.longitude}"
                        val lat = p.latitude
                        val lng = p.longitude
                        startMarker.position = p
                        Toast.makeText(context, "$lat $lng", Toast.LENGTH_LONG).show()
                        mapView.overlays.add(startMarker)

                        return true
                    }
                    return false
                }

                override fun longPressHelper(p: GeoPoint?): Boolean {
                    p?.let {
                        startMarker.position = p
                        mapView.overlays.add(startMarker)
                        Toast.makeText(context, "${p.latitude} ${p.longitude}", Toast.LENGTH_LONG).show()
                        return true
                    }
                    return false
                }
            }
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Configuration.getInstance()
                        .load(context, PreferenceManager.getDefaultSharedPreferences(context))
                    mapView.setTileSource(TileSourceFactory.MAPNIK)
                    mapView.setMultiTouchControls(true)
                    mapView.getLocalVisibleRect(Rect())

                    val rotationGestureOverlay = RotationGestureOverlay(mapView)
                    rotationGestureOverlay.isEnabled
                    mapView.overlays.add(rotationGestureOverlay)

                    mapView.overlays.add(mLocationOverlay)
                    mapView.overlays.add(mapEventsOverlay)
                    val controller = mapView.controller

                    controller.setCenter(mapCenter)
                    controller.setZoom(16.0)
                    mapView.minZoomLevel = 10.0
                    mapView.maxZoomLevel = 40.0
                    mapView.tilesScaleFactor = 1f




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
    val startPoint = GeoPoint(36.84924, 10.19023)
    val endPoint =  GeoPoint(36.8586, 10.19033)
    traceRoad(mapView, startPoint, endPoint)
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun traceRoad(mapView: MapView, startPoint: GeoPoint, endPoint: GeoPoint) {
    val roadManager = OSRMRoadManager(mapView.context, Configuration.getInstance().userAgentValue)
    val waypoints = ArrayList<GeoPoint>()
    waypoints.add(startPoint)
    waypoints.add(endPoint)

    // Launch the coroutine within the composable function
    LaunchedEffect(mapView) {
        try {
            // Perform the network operation inside the coroutine
            val road = withContext(Dispatchers.IO) {
                roadManager.getRoad(waypoints)
            }

            // Once the road data is fetched, update the UI on the main thread
            withContext(Dispatchers.Main) {
                // Create Polyline for road overlay
                val roadPolyline = Polyline().apply {
                    // Set color here (e.g., Color.RED)
                    outlinePaint.color = SkyBlueColor.toArgb()
                    // Add each point from the road to the Polyline
                    road.mRouteHigh.forEach { geoPoint ->
                        addPoint(geoPoint)
                    }
                }

                // Add the road Polyline to the map overlays
                mapView.overlays.add(roadPolyline)
                mapView.invalidate()
            }
        } catch (e: Exception) {
            // Handle exceptions here
            e.printStackTrace()
        }
    }
}

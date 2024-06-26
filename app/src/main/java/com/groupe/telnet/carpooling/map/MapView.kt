package com.groupe.telnet.carpooling.map

import android.graphics.Rect
import android.widget.FrameLayout
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.preference.PreferenceManager
import com.groupe.telnet.carpooling.map.iconButtons.addMarkertoMap
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


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

    val painter  = painterResource(R.drawable.ping).apply {
         Modifier
            .size(30.dp)
    }
    var mapCenter: IGeoPoint by rememberSaveable { mutableStateOf(GeoPoint(36.84924, 10.19023)) }

    val mGpsMyLocationProvider = GpsMyLocationProvider(context)
    val myLocOverlay = MyLocationNewOverlay(
        mGpsMyLocationProvider, mapView
    ).apply {
        enableMyLocation()
        enableFollowLocation()
    }


    val mapEventsOverlay = remember {
        MapEventsOverlay(
            object : MapEventsReceiver {

                override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                    p?.let {

                        mapView.overlays.forEach {
                            if (it is Marker) {
                                mapView.overlays.remove(it)
                            }
                        }
                        return true
                    }
                    return false
                }

                override fun longPressHelper(p: GeoPoint?): Boolean {
                    p?.let {
                        tappedLocation.value = "Latitude: ${p.latitude}, Longitude: ${p.longitude}"

                        mapView.overlays.forEach {
                            if (it is Marker) {
                                mapView.overlays.remove(it)
                            }
                        }
                        mapView.addMarkertoMap(
                            context,
                            p
                        )
                        // Toast.makeText(context, "${p.latitude} ${p.longitude}", Toast.LENGTH_LONG).show()


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




                    val controller = mapView.controller
                    controller.setCenter(mapCenter)
                    controller.setZoom(16.0)
                    mapView.minZoomLevel = 10.0
                    mapView.maxZoomLevel = 40.0
                    mapView.tilesScaleFactor = 1f
                    mapView.overlays.add(myLocOverlay)
                    mapView.overlays.add(mapEventsOverlay)





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

//    val startPoint = GeoPoint(36.84924, 10.19023)
//    val endPoint =  GeoPoint(36.8586, 10.19033)
//    traceRoad(mapView, startPoint, endPoint)

}


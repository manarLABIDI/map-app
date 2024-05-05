package com.groupe.telnet.carpooling.map.common.components

import android.content.Context
import android.widget.FrameLayout
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.preference.PreferenceManager
import com.groupe.telnet.carpooling.map.common.iconButtons.addMarkertoMap
import com.groupe.telnet.carpooling.map.presentation.viewModel.DestinationLocationViewModel
import kotlinx.coroutines.flow.collectLatest
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.*
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun MapComposable(
) {

    val destinationLocationViewModel : DestinationLocationViewModel = hiltViewModel()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
   // val destinationLocation by destinationLocationViewModel.endPoint.collectAsState()

    val mapView = remember { MapView(context) }
    var mapCenter by rememberSaveable { mutableStateOf(GeoPoint(36.84924, 10.19023)) }

    val mapEventsOverlay = remember {
        MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                return false
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                p?.let { location ->
                    destinationLocationViewModel.updateDestinationLocation(location)

                    return true
                }
                return false
            }
        })
    }


    setupMap(mapView, context, mapCenter, mapEventsOverlay)

    observeLifecycle(mapView, lifecycleOwner)

    LaunchedEffect(Unit) {
        destinationLocationViewModel.destinationEvent.collectLatest { destination ->
            mapView.overlays.removeAll { it is Marker }
            mapView.addMarkertoMap(context, destination)
            mapView.controller.animateTo(destination)

        }
    }




    AndroidView(factory = { mapView })
}

fun setupMap(
    mapView: MapView,
    context: Context,
    mapCenter: GeoPoint,
    mapEventsOverlay: MapEventsOverlay
) {
    mapView.apply {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        setTileSource(TileSourceFactory.MAPNIK)
        setMultiTouchControls(true)
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        controller.setZoom(16.0)
        minZoomLevel = 10.0
        maxZoomLevel = 40.0
        controller.setCenter(mapCenter)

        overlays.add(MyLocationNewOverlay(GpsMyLocationProvider(context), this).apply {
            enableMyLocation()
            enableFollowLocation()
        })

        overlays.add(mapEventsOverlay)
    }
}


@Composable
fun observeLifecycle(mapView: MapView, lifecycleOwner: LifecycleOwner) {
    val context = LocalContext.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    mapView.onResume()
                    Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
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
}

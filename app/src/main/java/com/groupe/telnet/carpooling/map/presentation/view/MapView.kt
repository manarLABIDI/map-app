package com.groupe.telnet.carpooling.map.presentation.view


import android.graphics.Rect
import android.widget.FrameLayout
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.preference.PreferenceManager
import com.groupe.telnet.carpooling.map.R
import com.groupe.telnet.carpooling.map.common.iconButtons.addMarkertoMap
import com.groupe.telnet.carpooling.map.presentation.viewModel.RoadPathViewModel
import kotlinx.coroutines.flow.collectLatest
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


@Composable
fun MapView(
    mapView: MapView,
    pinLocation : (GeoPoint) -> Unit,
    selectedLocation: State<GeoPoint?>
) {

    val  roadPathViewModel: RoadPathViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

     mapView.apply {

            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
     }

    LaunchedEffect(key1 = true) {
        roadPathViewModel.polylineFlow.collectLatest { polyline ->

            mapView.overlays.removeAll { it is Polyline }
            mapView.overlays.add(polyline)
            mapView.invalidate()
        }
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


                    return false
                }

                override fun longPressHelper(p: GeoPoint?): Boolean {
                    p?.let {
                       val  tappedLocation = p

                        mapView.overlays.removeAll { it is Polyline }
                        mapView.overlays.forEach {
                            if (it is Marker) {
                                mapView.overlays.remove(it)
                            }
                        }
                        mapView.addMarkertoMap(
                            context,
                            p
                        )
                        pinLocation(tappedLocation)


                        return true
                    }

                    return false
                }
            }
        )
    }
    LaunchedEffect(selectedLocation.value) {
        val location = selectedLocation.value
        if (location != null) {
            val pingLocation = Marker(mapView)
            pingLocation.position = location
            pingLocation.title = "AT : ${location.latitude}   ${location.longitude}"
            pingLocation.subDescription = "Selected location"
            pingLocation.icon = ContextCompat.getDrawable(context, R.drawable.ic_location)
                .apply { Modifier.size(5.dp) }
            mapView.overlays.add(pingLocation)
            mapView.controller
                .setCenter(location)

        }
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
                    mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)


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

}





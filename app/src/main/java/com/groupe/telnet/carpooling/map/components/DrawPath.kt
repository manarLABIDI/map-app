package com.groupe.telnet.carpooling.map.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import java.util.ArrayList


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DrawPath(mapView: MapView, startPoint: GeoPoint, endPoint: GeoPoint) {
    val roadManager = OSRMRoadManager(mapView.context, Configuration.getInstance().userAgentValue)
    val waypoints = ArrayList<GeoPoint>()
    waypoints.add(startPoint)
    waypoints.add(endPoint)

    LaunchedEffect(mapView) {
        try {
            val road = withContext(Dispatchers.IO) {
                roadManager.getRoad(waypoints)
            }


            withContext(Dispatchers.Main) {
                val roadPolyline = Polyline().apply {
                    outlinePaint.color = SkyBlueColor.toArgb()
                    road.mRouteHigh.forEach { geoPoint ->
                        addPoint(geoPoint)
                    }
                }

                mapView.overlays.add(roadPolyline)
                mapView.invalidate()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

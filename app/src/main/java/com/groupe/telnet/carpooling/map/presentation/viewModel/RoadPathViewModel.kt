package com.groupe.telnet.carpooling.map.presentation.viewModel

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline
import java.util.ArrayList
import javax.inject.Inject


@HiltViewModel
class RoadPathViewModel @Inject constructor(
    private val roadManager: OSRMRoadManager
) : ViewModel() {

    private val _polylineFlow = MutableSharedFlow<Polyline>()
    val polylineFlow = _polylineFlow.asSharedFlow()

    fun drawPath(startPoint: GeoPoint, endPoint: GeoPoint) {
        viewModelScope.launch {
            val waypoints = ArrayList<GeoPoint>()
            waypoints.add(startPoint)
            waypoints.add(endPoint)

            val road = withContext(Dispatchers.IO) {
                roadManager.getRoad(waypoints)
            }

            val roadPolyline = Polyline().apply {
                outlinePaint.color = SkyBlueColor.toArgb()
                road.mRouteHigh.forEach { point ->
                    addPoint(point)
                }
            }

            _polylineFlow.emit(roadPolyline)


        }
    }
}
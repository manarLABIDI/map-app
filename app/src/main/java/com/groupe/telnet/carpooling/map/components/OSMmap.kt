package com.groupe.telnet.carpooling.map.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline


@Composable
fun rememberMapView(context : Context): MapView {
    val mapView = remember{
        MapView(context)
    }
    // Cleanup the MapView when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mapView.onDetach()
        }
    }
    return mapView.apply {
        initMap(this)
    }
}

fun initMap(mapView : MapView){
    mapView.apply {
        isHorizontalMapRepetitionEnabled = false
        isVerticalMapRepetitionEnabled = false
        setMultiTouchControls(true)
        val tileSystem = MapView.getTileSystem()
        setScrollableAreaLimitDouble(
            BoundingBox(
                tileSystem.maxLatitude, tileSystem.maxLongitude, // top-left
                tileSystem.minLatitude, tileSystem.minLongitude  // bottom-right
            )
        )
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.getLocalVisibleRect(Rect())
        minZoomLevel = 4.0
        mapView.controller.setZoom(15.0)
    }
}


fun mapEventsOverlay(context : Context, view : MapView, onTap : (GeoPoint)->Unit ) : MapEventsOverlay {
    return MapEventsOverlay(object : MapEventsReceiver {
        override fun singleTapConfirmedHelper(geoPoint: GeoPoint?): Boolean {
            // Handle the map click event
            if (geoPoint != null) {
                onTap(geoPoint)
                view.invalidate() // Refresh the map view
            }
            return true
        }

        override fun longPressHelper(p: GeoPoint?): Boolean {
            // Handle long press event if needed
            return false
        }
    })
}

fun addMarker(
//    context : Context,
    mapView : MapView,
    geoPoint: GeoPoint,
    title: String?=null,
    snippet : String?=null,
    icon: Drawable?=null,
) : Marker {
    val marker = Marker(mapView)
    marker.position = geoPoint
    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

    marker.title = title
    if(snippet!=null){
        marker.snippet = snippet
    }

    if(icon!=null){
        marker.icon = icon
    }
//    marker.icon = customIcon

    mapView.overlays.add(marker)
    return marker
}


// Draw path bw two point
fun createPolyline(mapView: MapView, startPoint: GeoPoint, endPoint: GeoPoint): Polyline {
    val polyline = Polyline(mapView)
    polyline.addPoint(startPoint)
    polyline.addPoint(endPoint)
    polyline.infoWindow = null
    return polyline
}


private fun resizeDrawable(drawable: Drawable?, newWidth: Int, newHeight: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    drawable?.draw(canvas)
    return bitmap
}
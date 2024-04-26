package com.groupe.telnet.carpooling.map.common.iconButtons

import android.content.Context
import androidx.core.content.ContextCompat
import com.groupe.telnet.carpooling.map.R
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem


fun MapView.addMarkertoMap(
    context: Context,
    geoPoint: GeoPoint,
){
    val marker = Marker(this)
    marker.apply{
        position = geoPoint
        icon = ContextCompat.getDrawable(context, R.drawable.ic_location)
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        title = "AT : ${geoPoint.latitude}   ${geoPoint.longitude}"
        this.subDescription = "you marked a marker"
    }

    overlays.add(marker)
    invalidate()

}

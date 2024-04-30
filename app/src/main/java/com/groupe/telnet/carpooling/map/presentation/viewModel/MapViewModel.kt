package com.groupe.telnet.carpooling.map.presentation.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel(){

    private val _pinnedLocation = MutableStateFlow<GeoPoint?>(null)
    val pinnedLocation: StateFlow<GeoPoint?> = _pinnedLocation

    fun updatePinnedLocation(geoPoint: GeoPoint) {
        _pinnedLocation.value = geoPoint
    }
}
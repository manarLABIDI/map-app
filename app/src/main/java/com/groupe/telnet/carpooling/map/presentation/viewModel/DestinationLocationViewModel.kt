package com.groupe.telnet.carpooling.map.presentation.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe.telnet.carpooling.map.data.remote.api.SearchApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class DestinationLocationViewModel @Inject constructor(
) : ViewModel() {


    private val decimalFormat = DecimalFormat("#.#####")

    private var _locationInfo = mutableStateOf("")
    val locationInfo: State<String> = _locationInfo

    private val _endPoint = MutableStateFlow<GeoPoint?>(null)
    val endPoint: StateFlow<GeoPoint?> = _endPoint

    private val _destinationEvent = MutableSharedFlow<GeoPoint>()
    val destinationEvent: SharedFlow<GeoPoint> = _destinationEvent
    fun updateDestinationLocation(geoPoint: GeoPoint,  name: String? = null) {

        _endPoint.value = geoPoint
        _locationInfo.value = name ?: "${decimalFormat.format(geoPoint.latitude)}, ${decimalFormat.format(geoPoint.longitude)}"
        viewModelScope.launch {
            _destinationEvent.emit(geoPoint)
        }
    }


}
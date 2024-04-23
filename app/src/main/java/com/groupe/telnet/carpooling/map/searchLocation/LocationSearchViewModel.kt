package com.groupe.telnet.carpooling.map.searchLocation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe.telnet.carpooling.map.repository.NominatimApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val nominatimService: NominatimApi
) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<LocationResult>>(emptyList())
    val searchResults: StateFlow<List<LocationResult>> get() = _searchResults

    suspend fun searchLocation(query: String) {
        try {
            val results = nominatimService.search(query)
            _searchResults.value = results

            // Log the API response
            Log.d("LocationSearchViewModel", "Received ${results.size} results")
        } catch (e: Exception) {
            Log.e("LocationSearchViewModel", "Error searching location", e)
            _searchResults.value = emptyList() // Reset on error
        }
    }
}
package com.groupe.telnet.carpooling.map.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.groupe.telnet.carpooling.map.data.remote.api.SearchApi
import com.groupe.telnet.carpooling.map.data.remote.response.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val nominatimService: SearchApi
) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> get() = _searchResults

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
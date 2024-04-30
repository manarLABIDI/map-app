package com.groupe.telnet.carpooling.map.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe.telnet.carpooling.map.data.remote.api.SearchApi
import com.groupe.telnet.carpooling.map.data.remote.response.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val searchApi: SearchApi
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> get() = _searchResults
    private val _isSearchBarVisible = MutableStateFlow(false)
    val isSearchBarVisible: StateFlow<Boolean> = _isSearchBarVisible

    fun searchLocation(query: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val results = searchApi.search(query)
            _searchResults.value = results

            // Log the API response
            Log.d("LocationSearchViewModel", "Received ${results.size} results")
        } catch (e: Exception) {
            Log.e("LocationSearchViewModel", "Error searching location", e)
            _searchResults.value = emptyList()
        }
    }

    fun showSearchBar() {
        _isSearchBarVisible.update { true }
    }

    fun hideSearchBar() {
        _isSearchBarVisible.update { false }
    }


}
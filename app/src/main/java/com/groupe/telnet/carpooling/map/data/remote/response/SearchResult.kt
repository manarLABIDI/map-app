package com.groupe.telnet.carpooling.map.data.remote.response

data class SearchResult(
    val display_name: String,
    val address: String,
    val lat: String,
    val lon: String
)

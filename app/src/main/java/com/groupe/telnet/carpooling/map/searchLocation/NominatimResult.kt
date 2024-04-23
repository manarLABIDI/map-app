package com.groupe.telnet.carpooling.map.searchLocation

data class LocationResult(
    val display_name: String,
    val address: String,
    val lat: String,
    val lon: String
)

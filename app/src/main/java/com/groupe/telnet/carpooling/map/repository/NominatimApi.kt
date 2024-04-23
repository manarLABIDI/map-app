package com.groupe.telnet.carpooling.map.repository


import com.groupe.telnet.carpooling.map.searchLocation.LocationResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimApi {

    @GET("/search")
   suspend  fun search(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 7,
        @Query("accept-language") language: String = "en"

    ): List<LocationResult>


}

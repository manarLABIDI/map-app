package com.groupe.telnet.carpooling.map.data.remote.api


import com.groupe.telnet.carpooling.map.data.remote.response.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("/search")
   suspend  fun search(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 7,
        @Query("accept-language") language: String = "en"

    ): List<SearchResult>


}

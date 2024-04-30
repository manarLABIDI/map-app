package com.groupe.telnet.carpooling.map.common.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.groupe.telnet.carpooling.map.R
import com.groupe.telnet.carpooling.map.presentation.viewModel.DestinationLocationViewModel
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import com.groupe.telnet.carpooling.map.ui.theme.TextColor
import com.groupe.telnet.carpooling.map.presentation.viewModel.LocationSearchViewModel
import org.osmdroid.util.GeoPoint
import retrofit2.HttpException

@Composable
@OptIn(ExperimentalMaterial3Api::class)
 fun LocationSearchBar(
    vm: LocationSearchViewModel = hiltViewModel()
 ) {
    val destinationLocationViewModel : DestinationLocationViewModel = hiltViewModel()
    val searchResults by vm.searchResults.collectAsState()
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true) }
    val defaultSearchbarHorizontalPadding = 10.dp
    val searchBarColors = SearchBarDefaults.colors(
        containerColor = Color.White,
        dividerColor = SkyBlueColor,
        inputFieldColors = TextFieldDefaults.colors(
            TextColor,
            cursorColor = Color.Black
        )
    )
    var searchbarHorizontalPadding by remember { mutableStateOf(defaultSearchbarHorizontalPadding) }
    SearchBar(
        colors = searchBarColors,
        modifier = Modifier.fillMaxWidth()
            .padding(PaddingValues( 0.dp)),
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            try {
                vm.searchLocation(text)
            } catch (e: HttpException) {
                Log.e("LocationSearchBar", "HTTP error occurred: ${e.code()}", e) // Log HTTP exceptions
            } catch (e: Exception) {
                Log.e("LocationSearchBar", "An error occurred during search", e) // Log other exceptions
            }
        },
        active = active,
        onActiveChange = { isActive ->
            active = isActive
            searchbarHorizontalPadding = if (isActive) 0.dp else defaultSearchbarHorizontalPadding
        },
        placeholder = {
            Text(
                text = "Where to Go?",
                color = TextColor
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()) {
                            text = ""
                        } else {
                            active = false
                            searchbarHorizontalPadding = defaultSearchbarHorizontalPadding
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        },
        content = {
            LazyColumn {
                items(searchResults) { result ->
                    val latitude = result.lat.toDouble()
                    val longitude = result.lon.toDouble()
                    val name = result.name
                    var geoPoint =GeoPoint(latitude, longitude)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                destinationLocationViewModel.updateDestinationLocation(geoPoint, name)
                                active = false
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = result.display_name,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ping),
                            contentDescription = "Location Icon",
                            tint = SkyBlueColor,
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    )
}
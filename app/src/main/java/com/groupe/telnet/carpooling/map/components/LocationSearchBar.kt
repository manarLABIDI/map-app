package com.groupe.telnet.carpooling.map.components

import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import com.groupe.telnet.carpooling.map.R
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import com.groupe.telnet.carpooling.map.ui.theme.TextColor
import org.osmdroid.util.GeoPoint
import java.util.*

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LocationSearchBar() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val defaultSearchbarHorizontalPadding = 10.dp
    val searchBarColors = SearchBarDefaults.colors(
        containerColor = Color.White, // White background
        dividerColor = SkyBlueColor,
        inputFieldColors = TextFieldDefaults.colors(  TextColor,
            cursorColor = Color.Black
        )
    )
    var searchbarHorizontalPadding by remember { mutableStateOf(defaultSearchbarHorizontalPadding) }
    SearchBar(
        colors = searchBarColors,
        modifier = Modifier.fillMaxWidth()
            .padding(PaddingValues(searchbarHorizontalPadding, 0.dp)),
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = { isActive ->
            active = isActive
            searchbarHorizontalPadding = if (isActive) 0.dp else defaultSearchbarHorizontalPadding
        },
        placeholder = {
            Text(
                text = "Where to Go?",
                color = TextColor)
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
        }
    ) {

    }
}

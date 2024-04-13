package com.groupe.telnet.carpooling.map.components

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
import com.groupe.telnet.carpooling.map.R
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor


@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LocationSearchBar() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val defaultSearchbarHorizontalPadding = 10.dp
    var searchbarHorizontalPadding by remember { mutableStateOf(defaultSearchbarHorizontalPadding) }
    val searchHistory = listOf("kairouan", "sfax", "tunis", "sousse")
    SearchBar(
        modifier = Modifier.fillMaxWidth()
            .padding(PaddingValues(searchbarHorizontalPadding, 0.dp)),
        query = query,
        onQueryChange = {
            query = it
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
            Text(text = "Where to Go?")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (query.isNotEmpty()) {
                            query = ""
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
        searchHistory.takeLast(3).forEach { item ->
            ListItem(
                modifier = Modifier.clickable { query = item },
                headlineContent = { Text(text = item) },
                leadingContent = {
                    Icon(
                        painter = painterResource( R.drawable.ic_history),
                        contentDescription = null
                    )
                }
            )
        }

    }
}



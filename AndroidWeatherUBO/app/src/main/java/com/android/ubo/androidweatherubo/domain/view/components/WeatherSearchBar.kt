package com.android.ubo.androidweatherubo.domain.view.components

import android.location.Location
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.ubo.androidweatherubo.R

@Composable
fun WeatherSearchBar(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onLocateSearching: (value: Boolean) -> Unit = {},
    onLocateChange: (location: Location) -> Unit = {}
) {
    var text by remember { mutableStateOf(searchText) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = text,
            label = { Text(placeholderText) },
            onValueChange = {
                text = it
                onSearchTextChanged(it)
                            },
        )
        LocationPermissionIcon(
            locationChange = onLocateChange,
            locationSearching = onLocateSearching
        )
        IconButton(
            onClick = {
                text = ""
                onClearClick()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(id = R.string.icn_search_clear_content_description)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherSearchBarPreview() {
    WeatherSearchBar(
        searchText = "Brest",
        placeholderText = stringResource(id = R.string.search_placeholder),
        onSearchTextChanged = { text ->
            println("Text change : $text")
        },
        onClearClick = {
            println("Clicked")
        })
}
package com.example.wheatherApplication.domain.view.component

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import com.example.wheatherApplication.R
import io.ktor.websocket.Frame

@Composable
fun WeatherSearchBar(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {}
) {
    var text by remember { mutableStateOf(searchText) }
    Column {
        OutlinedTextField(
            value = text,
            label = { Text(placeholderText) },
            onValueChange = {
                text = it
                onSearchTextChanged(it)
            }
        )
        IconButton(
            onClick = { onClearClick() }
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(id = R.string.icn_search_clear_content_description)
            )
        }
    }
}
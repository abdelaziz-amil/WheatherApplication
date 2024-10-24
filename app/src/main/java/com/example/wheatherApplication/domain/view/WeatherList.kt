package com.example.wheatherApplication.domain.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.wheatherApplication.domain.view.component.WeatherDetailsItem
import com.example.wheatherApplication.domain.view.component.WeatherSearchBar
import org.koin.androidx.compose.getViewModel

@Composable
fun WeatherList(name: String) {
    val weatherViewModel = getViewModel<WeatherViewModel>()
    val state by weatherViewModel.viewState.collectAsState()
    var title = state.city?.name ?: "pas de ville bouuuu"
    LaunchedEffect(true) {
        weatherViewModel.cityChanged(name)
    }

    WeatherSearchBar(
        searchText = "",
        onSearchTextChanged = {text ->
            weatherViewModel.cityChanged(text)
        },
        onClearClick = {},
        placeholderText = "Search a city"
    )

    Column() {
        Text(text = title + "!")
        state.items.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                items(items = it, itemContent = { item ->
                    Row() {
                        Text(text = "Le ${item.day} à ${item.hour}H ==> ")
                        WeatherDetailsItem(
                            modifier = Modifier,
                            city = state.city!!,
                            item = item,
                        )
                    }
                })
            }
        }
    }
}
package com.android.ubo.androidweatherubo.domain.view.list

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
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherList(name: String) {
    val weatherViewModel = koinViewModel<WeatherViewModel>()
    val state by weatherViewModel.viewState.collectAsState()

    LaunchedEffect(true) {
        weatherViewModel.cityChanged(name)
    }

    Column() {
        Text(text = "Hello $name!")
        if (state.isLoading) {
            Text("chargement en cours")
        }
        state.items.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                items(items = it, itemContent = { item ->
                    Row() {
                        Text(text = "Le ${item.day} à ${item.hour}H ==> ")
                        Text(
                            text =
                            when (item.image) {
                                "1" -> "beau"
                                "2" -> "moyen"
                                "3" -> "pluie"
                                else -> ":/"
                            }
                        )
                    }
                })
            }
        }
    }
}
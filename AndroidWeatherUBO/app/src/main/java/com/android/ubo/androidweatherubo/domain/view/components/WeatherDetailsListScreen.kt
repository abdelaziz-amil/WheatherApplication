package com.android.ubo.androidweatherubo.domain.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.android.ubo.androidweatherubo.R
import com.android.ubo.androidweatherubo.domain.navigation.Screen
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.ubo.androidweatherubo.domain.model.WeatherItemDomain
import com.android.ubo.androidweatherubo.domain.view.weatherDetailListViewModel.WeatherDetailListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailsListScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    cityName: String
) {
    val weatherViewModel = koinViewModel<WeatherDetailListViewModel>()
    val state by weatherViewModel.viewState.collectAsState()
    LaunchedEffect(true) {
        weatherViewModel.cityChanged(cityName)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.list_title_label, cityName ?: ""))
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            modifier = Modifier,
                            contentDescription = stringResource(id = R.string.icn_search_back_content_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Button(onClick = {
                navHostController.navigate(route = "${Screen.WeatherSearch.route}")
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.icn_go_to_details)
                )
            }
            WeatherList(weatherItems = state.items)
        }
    }
}

@Composable
fun WeatherList(weatherItems: List<WeatherItemDomain>){
    LazyColumn {
        items(weatherItems) { item ->
            WeatherListRendererItem(item)
        }
    }
}

@Composable
fun WeatherListRendererItem(weatherItem: WeatherItemDomain) {
    Card {
        Row {
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = R.string.details_hour_day_label, weatherItem.day, weatherItem.hour)
            )
            Image(
                painter = painterResource(
                    id = when (weatherItem.infos.humidity) {
                        in 0.0..50.0 -> R.drawable.ic_sun
                        in 50.0..80.0 -> R.drawable.ic_cloud
                        in 80.0..90.0 -> R.drawable.ic_cloud_gray
                        else -> R.drawable.ic_rain
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(100.dp).aspectRatio(1F)
            )
            Column {
                Text(text = "${weatherItem.infos.temp.toString()} °")
                Text(text = "${weatherItem.infos.humidity.toString()} %")
            }
        }
    }
}
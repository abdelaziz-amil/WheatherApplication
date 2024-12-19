package com.android.ubo.androidweatherubo.domain.view.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.ubo.androidweatherubo.R
import com.android.ubo.androidweatherubo.domain.navigation.Screen
import com.android.ubo.androidweatherubo.domain.view.components.WeatherDetailsItem
import com.android.ubo.androidweatherubo.domain.view.components.WeatherSearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherSearchScreen(
    modifier: Modifier,
    navHostController: NavHostController
) {
    val weatherViewModel = koinViewModel<WeatherSearchScreenViewModel>()
    val state by weatherViewModel.viewState.collectAsState()
    var locationSearching by remember { mutableStateOf(false) }

    Column (modifier = modifier) {
        WeatherSearchBar(
            searchText = "",
            placeholderText = stringResource(id = R.string.search_placeholder),
            onSearchTextChanged = { text ->
                weatherViewModel.cityChanged(text)
            },
            onClearClick = {
                weatherViewModel.clear()
            },
            onLocateSearching = {
                locationSearching = it
            },
            onLocateChange = {
                weatherViewModel.locationChanged(it)
            }
        )
        Button(onClick = {
            navHostController.navigate(route = "${Screen.WeatherList.route}/${state.city?.name}")
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(id = R.string.icn_go_to_details)
            )
        }
        if (state.isLoading || locationSearching) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                CircularProgressIndicator()
                Text(
                    text = stringResource(
                        id =  if (state.isLoading) {
                            R.string.search_processing_label
                        } else {
                            R.string.localisation_processing_label
                        }
                    )
                )
            }
        } else if (state.city != null && state.first != null) {
            WeatherDetailsItem(
                modifier = Modifier.fillMaxWidth().padding(50.dp),
                city = state.city!!,
                item = state.first!!
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.weather_question
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                )
                Text(
                    text = stringResource(id = R.string.entry_label),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }

}
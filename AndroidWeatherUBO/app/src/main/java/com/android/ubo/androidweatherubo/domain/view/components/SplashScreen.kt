package com.example.weather.domain.view.splashScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.ubo.androidweatherubo.R
import com.android.ubo.androidweatherubo.domain.navigation.Screen

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val raw = R.raw.papa_meteo
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw))
    var isPlaying by remember { mutableStateOf(true) }
    val progress by
    animateLottieCompositionAsState(composition = composition, isPlaying = isPlaying)

    LaunchedEffect(key1 = progress) {
        if (progress == 0f) {
            isPlaying = true
        }
        if (progress == 1f) {
            isPlaying = false
            navHostController.navigate(route = Screen.WeatherSearch.route)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(300.dp),
            composition = composition,
            progress = { progress }
        )
    }
}
package com.android.ubo.androidweatherubo.domain.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.ubo.androidweatherubo.domain.view.search.WeatherSearchScreen
import com.android.ubo.androidweatherubo.domain.view.components.WeatherDetailsListScreen
import androidx.compose.ui.Modifier

@Composable
fun SetupNavGraph(modifier: Modifier,
                  navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.WeatherSearch.route
    ) {
        composable(
            route = Screen.WeatherSearch.route
        ) {
            WeatherSearchScreen(
                modifier = modifier,
                navHostController = navHostController
            )
        }
        composable(
            route = "${Screen.WeatherList.route}/{cityName}",
            arguments = listOf(navArgument("cityName") { type = NavType.StringType })
        ) {
            WeatherDetailsListScreen(
                modifier= modifier,
                navHostController = navHostController,
                cityName = it.arguments?.getString("cityName") ?: ""
            )
        }
    }
}

sealed class Screen(val route: String) {
    object WeatherSearch : Screen("WeatherSearch")
    object WeatherList : Screen("WeatherList")
}
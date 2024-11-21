package com.android.ubo.androidweatherubo.core

import android.util.Log
import com.android.ubo.androidweatherubo.WeatherApplication
import com.android.ubo.androidweatherubo.data.api.OpenWeatherApi
import com.android.ubo.androidweatherubo.data.repository.WeatherRepository
import com.android.ubo.androidweatherubo.data.repository.WeatherRepositoryInterface
import com.android.ubo.androidweatherubo.domain.view.list.WeatherViewModel
import com.android.ubo.androidweatherubo.domain.view.search.WeatherSearchScreen
import com.android.ubo.androidweatherubo.domain.view.search.WeatherSearchScreenViewModel
import com.android.ubo.androidweatherubo.domain.view.weatherDetailListViewModel.LocationViewModel
import com.android.ubo.androidweatherubo.domain.view.weatherDetailListViewModel.WeatherDetailListViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private inline val requireApplication
    get() = WeatherApplication.instance ?: error("Missing call: initWith(application)")
val appModule = module {
    single<String>(named("weather_api_key")) { "888f70e84a4d7e44f3c0d4870c926e9d" }
    single<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(requireApplication.applicationContext) }
    viewModel { LocationViewModel(client = get(), context = get()) }
    viewModel { WeatherViewModel(repository = get()) }
    viewModel { WeatherDetailListViewModel(repository = get()) }
    viewModel { WeatherSearchScreenViewModel(repository = get()) }
}

val commonModule = module {
    single { Android.create() }
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = true) }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    single<WeatherRepositoryInterface> { WeatherRepository(get()) }
    single { OpenWeatherApi(get(), get(named("weather_api_key"))) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) =
    HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = CustomHttpLogger()
                level = LogLevel.ALL
            }
        }
    }

class CustomHttpLogger() : Logger {
    override fun log(message: String) {
        Log.i("CustomHttpLogger", "message : $message")
    }
}
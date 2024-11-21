package com.android.ubo.androidweatherubo.data.repository

import com.android.ubo.androidweatherubo.data.utils.Resource
import com.android.ubo.androidweatherubo.domain.model.WeatherResultDomain
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    suspend fun fetchWeather(city: String): Flow<Resource<WeatherResultDomain?>>
    suspend fun fetchWeather(lat: Double, lon: Double): Flow<Resource<WeatherResultDomain?>>
}
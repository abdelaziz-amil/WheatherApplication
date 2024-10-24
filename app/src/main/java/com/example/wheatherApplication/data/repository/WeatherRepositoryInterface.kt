package com.example.wheatherApplication.data.repository

import com.example.wheatherApplication.data.utils.Resource
import com.example.wheatherApplication.domain.model.WeatherResultDomain
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    suspend fun fetchWeather(city: String): Flow<Resource<WeatherResultDomain?>>
    suspend fun fetchWeather(lat: Double, lon: Double): Flow<Resource<WeatherResultDomain?>>
}
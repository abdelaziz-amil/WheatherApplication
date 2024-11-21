package com.android.ubo.androidweatherubo.data.repository

import com.android.ubo.androidweatherubo.data.api.OpenWeatherApi
import com.android.ubo.androidweatherubo.data.utils.Resource
import com.android.ubo.androidweatherubo.data.vo.WeatherResultVO
import com.android.ubo.androidweatherubo.domain.model.WeatherResultDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class WeatherRepository(
    private val openWeatherApi: OpenWeatherApi
): WeatherRepositoryInterface, KoinComponent {
    override suspend fun fetchWeather(city: String): Flow<Resource<WeatherResultDomain?>> {
        return fetchWeatherForAll(lat = null, lon = null, city = city)
    }

    override suspend fun fetchWeather(
        lat: Double,
        lon: Double
    ): Flow<Resource<WeatherResultDomain?>> = fetchWeatherForAll(lat = lat, lon = lon, city = null)

    private suspend fun fetchWeatherForAll(lat: Double?,
                                           lon: Double?,
                                           city : String?) : Flow<Resource<WeatherResultDomain?>> {
        return flow {
            emit(Resource.Loading())

            val response : WeatherResultVO? = if (city != null) {
                openWeatherApi.fetchWeather(city)
            } else if (lat != null && lon != null) {
                openWeatherApi.fetchWeather(lat, lon)
            } else {
                null
            }

            val domain : WeatherResultDomain? = response?.toDomain()
            if (domain != null) {
                emit(Resource.Success(domain))
            } else {
                emit(Resource.Error(Exception("NO DATA")))
            }
        }
    }

}
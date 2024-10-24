package com.example.wheatherApplication.data.vo

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResultVO(
    val city: WeatherCityVO? = null,
    val list: List<WeatherItemVO>? = listOf()
)

@Serializable
data class WeatherCityVO(
    val name: String,
    val coord: CoordinateVO,
    var population: Int,
    val sunrise: Double? = null,
    val sunset: Double? = null
)

@Serializable
data class CoordinateVO(val lat: Double? = null, val lon: Double? = null)

@Serializable
data class WeatherItemVO(val dt_txt: String, val dt: Float, val main: WeatherInfoVO)

@Serializable
data class WeatherInfoVO(val temp: Double, val humidity: Double, val pressure: Double)
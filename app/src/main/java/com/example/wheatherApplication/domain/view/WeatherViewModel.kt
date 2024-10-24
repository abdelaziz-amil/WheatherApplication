package com.example.wheatherApplication.domain.view

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherApplication.data.repository.WeatherRepositoryInterface
import com.example.wheatherApplication.data.utils.Status
import com.example.wheatherApplication.domain.model.WeatherCityDomain
import com.example.wheatherApplication.domain.model.WeatherItemDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WeatherViewModelState(
    var city: WeatherCityDomain? = null,
    var first: WeatherItemDomain? = null,
    var items: List<WeatherItemDomain> = emptyList(),
    var isLoading: Boolean = false,
)

class WeatherViewModel(private val repository: WeatherRepositoryInterface) :
    ViewModel() {

    private val _viewState = MutableStateFlow<WeatherViewModelState>(WeatherViewModelState())
    var viewState = _viewState.asStateFlow()

    fun cityChanged(value: String) = fetchWeather(value)

    fun locationChanged(value: Location) = fetchWeather(value)

    private fun fetchWeather(location: Location) = fetchWithFlow(null, location)

    private fun fetchWeather(city: String) = fetchWithFlow(city)

    private fun fetchWithFlow(city: String? = null, location: Location? = null) {
        viewModelScope.launch {
            var flow = if (city != null) {
                repository.fetchWeather(city)
            } else if (location!=null) {
                repository.fetchWeather(lat = location.latitude, lon = location.longitude)
            } else {
                null
            }
            flow?.collect { resource ->
                _viewState.update {
                    WeatherViewModelState(
                        city = resource.data?.city,
                        first = resource.data?.items?.firstOrNull(),
                        items = resource.data?.items ?: emptyList(),
                        isLoading = resource.status == Status.LOADING
                    )
                }
            }
        }
    }
}
package com.android.ubo.androidweatherubo.domain.view.list

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ubo.androidweatherubo.data.repository.WeatherRepositoryInterface
import com.android.ubo.androidweatherubo.data.utils.Status
import com.android.ubo.androidweatherubo.domain.model.WeatherCityDomain
import com.android.ubo.androidweatherubo.domain.model.WeatherItemDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WeatherViewModelState(
    var city: WeatherCityDomain? = null,
    var first: WeatherItemDomain? = null,
    var items: List<WeatherItemDomain> = emptyList(),
    var isLoading: Boolean = false,
)

open class WeatherViewModel(private val repository: WeatherRepositoryInterface) :
    ViewModel() {

    private val _viewState = MutableStateFlow<WeatherViewModelState>(WeatherViewModelState())
    var viewState = _viewState.asStateFlow()

    fun cityChanged(value: String) = fetchWeather(value)

    fun locationChanged(value: Location) = fetchWeather(value)

    private fun fetchWeather(location: Location) = fetchWithFlow(null, location)

    private fun fetchWeather(city: String) = fetchWithFlow(city)

    private fun fetchWithFlow(city: String? = null, location: Location? = null) {
        viewModelScope.launch {
            val flow = if (city != null) {
                repository.fetchWeather(city)
            } else if (location != null) {
                repository.fetchWeather(lat = location.latitude, lon= location.longitude)
            } else {
                null
            }

            flow?.collect { resource ->
                val items = resource.data?.items ?: emptyList()
                _viewState.update {
                    WeatherViewModelState(
                        city = resource.data?.city,
                        first = items.firstOrNull(),
                        items = items,
                        isLoading = resource.status == Status.LOADING
                    )
                }
            }
        }
    }
}
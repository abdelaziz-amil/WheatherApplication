package com.android.ubo.androidweatherubo.domain.view.weatherDetailListViewModel

import com.android.ubo.androidweatherubo.data.repository.WeatherRepositoryInterface
import com.android.ubo.androidweatherubo.domain.view.list.WeatherViewModel

class WeatherDetailListViewModel(repository: WeatherRepositoryInterface) : WeatherViewModel(
    repository = repository
) {

}
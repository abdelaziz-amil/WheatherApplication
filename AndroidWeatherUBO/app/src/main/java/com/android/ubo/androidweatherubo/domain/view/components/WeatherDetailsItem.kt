package com.android.ubo.androidweatherubo.domain.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.ubo.androidweatherubo.R
import com.android.ubo.androidweatherubo.domain.model.CoordinateDomain
import com.android.ubo.androidweatherubo.domain.model.WeatherCityDomain
import com.android.ubo.androidweatherubo.domain.model.WeatherInfoDomain
import com.android.ubo.androidweatherubo.domain.model.WeatherItemDomain
import java.time.LocalDateTime

@Composable
fun WeatherDetailsItem(
    modifier: Modifier,
    city: WeatherCityDomain,
    item: WeatherItemDomain
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.details_city_label,
                city.name
            )
        )

        Text(
            text = stringResource(
                id = R.string.details_hour_label,
                item.date.hour
            )
        )

        Image(
            painter = painterResource(
                id = when (item.infos.humidity) {
                    in 0.0..50.0 -> R.drawable.ic_sun
                    in 50.0..80.0 -> R.drawable.ic_cloud
                    in 80.0..90.0 -> R.drawable.ic_cloud_gray
                    else -> R.drawable.ic_rain
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(100.dp).aspectRatio(1F)
        )

        Text(
            text = stringResource(
                id = R.string.details_infos_label,
                item.infos.temp,
                item.infos.humidity
            )
        )

    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDetailsItemPreview() {
    WeatherDetailsItem(
        modifier = Modifier.fillMaxWidth().padding(50.dp),
        city = WeatherCityDomain(
            name = "Brest",
            coord = CoordinateDomain(lat = 48.0, lon = 14.0),
            population = 150000
        ),
        item = WeatherItemDomain(
            date = LocalDateTime.now(),
            image = "1",
            infos = WeatherInfoDomain(
                temp = 19.0,
                humidity = 60.0,
                pressure = 10.0
            )
        )
    )
}
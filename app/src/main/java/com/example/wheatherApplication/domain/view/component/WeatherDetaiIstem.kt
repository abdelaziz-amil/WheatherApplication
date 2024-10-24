package com.example.wheatherApplication.domain.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wheatherApplication.R
import com.example.wheatherApplication.domain.model.WeatherCityDomain
import com.example.wheatherApplication.domain.model.WeatherItemDomain
@Composable
fun WeatherDetailsItem(
    modifier: Modifier,
    city: WeatherCityDomain,
    item: WeatherItemDomain
){
    Column (modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Text( text = stringResource(
            id = R.string.details_city_label,
            city.name
        ))
        Text( text = stringResource(
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

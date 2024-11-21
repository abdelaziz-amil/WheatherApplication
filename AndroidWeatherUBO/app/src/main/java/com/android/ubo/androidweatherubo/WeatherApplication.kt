package com.android.ubo.androidweatherubo

import android.app.Application
import com.android.ubo.androidweatherubo.core.appModule
import com.android.ubo.androidweatherubo.core.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WeatherApplication : Application() {

    companion object {
        var instance: WeatherApplication? = null
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            modules(appModule)
            modules(commonModule)
        }
    }
}
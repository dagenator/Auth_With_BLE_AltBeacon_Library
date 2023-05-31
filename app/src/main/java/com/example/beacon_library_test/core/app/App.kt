package com.example.beacon_library_test.core.app

import android.app.Application
import com.example.beacon_library_test.core.di.AppComponent
import com.example.beacon_library_test.core.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}
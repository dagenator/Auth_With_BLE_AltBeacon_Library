package com.example.beacon_library_test.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import org.altbeacon.beacon.BeaconManager
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.baseContext

    @Singleton
    @Provides
    fun provideBeaconManager(context: Context): BeaconManager =
        BeaconManager.getInstanceForApplication(context)

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(AppModule.PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    companion object {
        const val PREFERENCES_KEY = "PREFERENCES_MVP_BLE"
    }
}
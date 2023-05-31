package com.example.beacon_library_test.core.di

import android.app.Application
import com.example.beacon_library_test.AuthActivity
import com.example.beacon_library_test.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, BindingModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(authActivity: AuthActivity)
    fun inject(app: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
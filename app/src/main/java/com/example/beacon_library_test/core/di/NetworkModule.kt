package com.example.beacon_library_test.core.di

import com.example.beacon_library_test.core.interceptors.AuthInterceptor
import com.example.beacon_library_test.data.api.SomeIntercomApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideUVRetrofit(authInterceptor: AuthInterceptor): SomeIntercomApi {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(authInterceptor)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("yourBaseUrl")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        return retrofit
            .create(SomeIntercomApi::class.java)

    }
}

package com.example.beacon_library_test.core.interceptors

import com.example.beacon_library_test.utils.UserTokenStoragePreferencesUtils
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private var tokenUtils: UserTokenStoragePreferencesUtils) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = tokenUtils.getToken()

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${token?.token ?: ""}")
            .build()

        return chain.proceed(newRequest)
    }
}
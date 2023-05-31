package com.example.beacon_library_test.data.api

import com.example.beacon_library_test.data.models.ApiAccess
import com.example.beacon_library_test.data.models.IntercomOpeningResult
import com.example.beacon_library_test.data.models.IntercomRelays
import retrofit2.http.*


interface SomeIntercomApi {

    //cleaned ap api because of intersvaz nda
    @FormUrlEncoded
    @POST("***************")
    suspend fun auth(
    ): ApiAccess

    @GET("*****************")
    suspend fun getRelays(
    ): List<IntercomRelays>

    @POST("*****************")
    suspend fun openIntercom(
    ): IntercomOpeningResult

}
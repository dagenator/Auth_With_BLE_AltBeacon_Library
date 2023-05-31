package com.example.beacon_library_test.data.models

import com.google.gson.annotations.SerializedName

data class ApiAccess(
    @SerializedName("*****") val userId: Int,
    @SerializedName("*****") val startTime: String,
    @SerializedName("*****") val endTime: String,
    @SerializedName("*****") val token: String
)


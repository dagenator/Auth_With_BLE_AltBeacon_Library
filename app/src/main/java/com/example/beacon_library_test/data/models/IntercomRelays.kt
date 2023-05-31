package com.example.beacon_library_test.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class IntercomRelays(
    @SerializedName("ADDRESS") var address: String = "",

    @SerializedName("RELAY_ID") var relayId: String = "",

    @SerializedName("IS_MAIN") var main: Int = 0,

    @SerializedName("MAC_ADDR") var mac: String? = null,

    @SerializedName("PORCH_NUM") var entranceNum: Int? = null,

    @SerializedName("RELAY_DESCR") var relayDesc: String? = null,
) : Serializable

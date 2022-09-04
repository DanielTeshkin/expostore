package com.expostore.data.remote.api.request

import com.google.gson.annotations.SerializedName

/**
 * @author Fedotov Yakov
 */
data class RefreshTokenRequest(
    @field:SerializedName("refresh")
    val refresh: String
)
package com.expostore.data.remote.api.pojo.signup

import com.google.gson.annotations.SerializedName

data class SignUpRequestData(
    @SerializedName("username") val username: String?,
    @SerializedName("password") val password: String?
)

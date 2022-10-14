package com.expostore.data.remote.api.pojo.signup

import com.google.gson.annotations.SerializedName

data class SignUpRequestData(
    @SerializedName("username") val username: String?,
    @SerializedName("password") val password: String?
)

data class ResetPasswordRequest(
    @SerializedName("username") val username: String?="",
    @SerializedName("password1") val password1: String?="",
    @SerializedName("password2") val password2: String?=""
)

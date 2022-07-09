package com.expostore.api.pojo.signup

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class SignUpRequestData(
    @SerializedName("username") val username: String?,
    @SerializedName("password") val password: String?
)

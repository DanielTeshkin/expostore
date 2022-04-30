package com.expostore.api.pojo.signup

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class SignUpResponseData(
    @SerializedName("refresh") val refresh: String?="",
    @SerializedName("access") val access: String?=""
)

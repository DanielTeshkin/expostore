package com.expostore.api.pojo.confirmcode

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class ConfirmCodeRequestData(
    @SerializedName("phone") val phone: String?,
    @SerializedName("code") val code: String?
)

package com.expostore.api.pojo.confirmnumber

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class ConfirmNumberRequestData(
    @SerializedName("phone") val phone: String?=""
)

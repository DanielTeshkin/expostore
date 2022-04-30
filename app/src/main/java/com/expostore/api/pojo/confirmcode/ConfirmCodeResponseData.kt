package com.expostore.api.pojo.confirmcode

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class ConfirmCodeResponseData(
    @SerializedName("message") val message: String?=""
    )

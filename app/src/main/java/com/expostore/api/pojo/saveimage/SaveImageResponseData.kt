package com.expostore.api.pojo.saveimage

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class SaveImageResponseData(
        @SerializedName("id") val id: String=""
)

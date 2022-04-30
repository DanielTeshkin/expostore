package com.expostore.api.pojo.saveimage

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class SaveImageRequestData(
        @SerializedName("image") val image: String="",
        @SerializedName("extensions") val extensions: String=""
)

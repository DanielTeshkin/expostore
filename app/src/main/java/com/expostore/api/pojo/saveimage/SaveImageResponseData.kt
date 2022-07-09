package com.expostore.api.pojo.saveimage

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class SaveImageResponseData(
        @SerializedName("images_id") val id: List<String> = listOf()
)

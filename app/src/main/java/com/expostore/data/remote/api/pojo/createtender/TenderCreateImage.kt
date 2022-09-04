package com.expostore.data.remote.api.pojo.createtender

import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.MultipartBody

data class TenderCreateImage(
    @JsonProperty("image") val image: MultipartBody.Part,
    @JsonProperty("title") val title: String
)

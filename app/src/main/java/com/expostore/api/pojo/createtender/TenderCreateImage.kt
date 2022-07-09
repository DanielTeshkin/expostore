package com.expostore.api.pojo.createtender

import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class TenderCreateImage(
    @JsonProperty("image") val image: MultipartBody.Part,
    @JsonProperty("title") val title: String
)

package com.expostore.api.pojo.createtender

import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.MultipartBody

data class CreateTenderResponseData(
        @JsonProperty("title") val title: String="",
        @JsonProperty("description") val description: String="",
        @JsonProperty("price_from") val priceFrom: String="",
        @JsonProperty("price_up_to") val priceUpTo: String="",
        @JsonProperty("location") val location: String="",
        @JsonProperty("images") val images: ArrayList<String> = listOf<String>() as ArrayList<String>,
        @JsonProperty("category") val category: String="",
        @JsonProperty("author") val author: String="",


)

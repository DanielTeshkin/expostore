package com.expostore.api.pojo.createtender

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTenderRequestData(
        @JsonProperty("title") val title: String,
        @JsonProperty("description") val description: String,
        @JsonProperty("price_from") val priceFrom: String,
        @JsonProperty("price_up_to") val priceUpTo: String,
        @JsonProperty("location") val location: String,
        @JsonProperty("images") val images: ArrayList<String>,
        @JsonProperty("category") val category: ArrayList<String>
)

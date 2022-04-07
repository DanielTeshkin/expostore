package com.expostore.api.pojo.gettenderlist

import com.expostore.api.pojo.getcategory.ImageResponseData
import com.fasterxml.jackson.annotation.JsonProperty

data class Tender(
    @JsonProperty("id") val id: String,
    @JsonProperty("title") val title: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("price_from") val priceFrom: String?,
    @JsonProperty("price_up_to") val priceUpTo: String?,
    @JsonProperty("location") val location: String?,
    @JsonProperty("author") val author: String,
    @JsonProperty("images") val images: ArrayList<ImageResponseData>,
    @JsonProperty("category") val category: ArrayList<TenderCategory>
)

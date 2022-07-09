package com.expostore.api.pojo.addreview

import com.fasterxml.jackson.annotation.JsonProperty

data class AddReviewResponseData(
        @JsonProperty("id") val id: String,
        @JsonProperty("rating") val rating: Int,
        @JsonProperty("text") val text: String?,
        @JsonProperty("date_created") val dateCreated: String,
        @JsonProperty("author") val author: String,
        @JsonProperty("author") val product: String,
        @JsonProperty("images") val images: ArrayList<String>?
)

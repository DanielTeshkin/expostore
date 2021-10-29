package com.expostore.api.pojo.addreview

import com.fasterxml.jackson.annotation.JsonProperty

data class AddReviewRequestData(
    @JsonProperty("rating") val rating: Int,
    @JsonProperty("text") val text: String?,
    @JsonProperty("images") val images: ArrayList<String>
)

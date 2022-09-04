package com.expostore.data.remote.api.pojo.getreviews

import com.fasterxml.jackson.annotation.JsonProperty

data class ReviewsResponseData(
        @JsonProperty("count") val count: Int?,
        @JsonProperty("next") val next: String?,
        @JsonProperty("previous") val previous: String?,
        @JsonProperty("results") val results: ArrayList<Review>?,
)

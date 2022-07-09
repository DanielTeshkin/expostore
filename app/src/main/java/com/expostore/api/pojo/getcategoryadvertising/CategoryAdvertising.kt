package com.expostore.api.pojo.getcategoryadvertising

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryAdvertising(
    @JsonProperty("id") val id: String?,
    @JsonProperty("image") val image: String?,
    @JsonProperty("url") val url: String?,
    @JsonProperty("date_created") val dateCreated: String?
)

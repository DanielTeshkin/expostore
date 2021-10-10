package com.expostore.api.pojo.gettenderlist

import com.fasterxml.jackson.annotation.JsonProperty

data class TenderCategory(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String?,
        @JsonProperty("sorting_number") val sortingNumber: Int?,
        @JsonProperty("parent") val parent: String?
)
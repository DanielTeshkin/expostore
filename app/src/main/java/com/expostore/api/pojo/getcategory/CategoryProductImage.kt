package com.expostore.api.pojo.getcategory

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryProductImage(
    @JsonProperty("id") val id: String?,
    @JsonProperty("file") val file: String?
)

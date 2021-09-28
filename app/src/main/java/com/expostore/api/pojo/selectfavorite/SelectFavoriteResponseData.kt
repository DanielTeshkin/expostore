package com.expostore.api.pojo.selectfavorite

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.fasterxml.jackson.annotation.JsonProperty

data class SelectFavoriteResponseData(
    @JsonProperty("id") val id: String?,
    @JsonProperty("notes") val notes: String?,
    @JsonProperty("user") val user: String?,
    @JsonProperty("product") val product: String?
)
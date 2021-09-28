package com.expostore.api.pojo.getfavoriteslist

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.fasterxml.jackson.annotation.JsonProperty

data class GetFavoritesListResponseData(
    @JsonProperty("id") val id: String?,
    @JsonProperty("product") val product: CategoryProduct,
    @JsonProperty("notes") val notes: String?,
    @JsonProperty("user") val user: String?
)

package com.expostore.api.pojo.getcategory

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryProduct(
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("images") var images: ArrayList<CategoryProductImage>?,
    @JsonProperty("price") val price: String?,
    @JsonProperty("rating") val rating: Int?,
    @JsonProperty("promotion") val promotion: String?,
    @JsonProperty("is_liked") val like: Boolean
    )

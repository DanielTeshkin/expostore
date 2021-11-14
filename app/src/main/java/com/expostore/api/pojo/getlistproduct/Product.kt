package com.expostore.api.pojo.getlistproduct

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.pojo.getcategory.CategoryProductImage
import com.fasterxml.jackson.annotation.JsonProperty

data class Product(
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val name: String,
    @JsonProperty("images") val images: ArrayList<CategoryProductImage>?,
    @JsonProperty("price") val price: String?,
    @JsonProperty("rating") val rating: String?,
    @JsonProperty("promotion") val promotion: String?,
    @JsonProperty("is_liked") val like: Boolean
)


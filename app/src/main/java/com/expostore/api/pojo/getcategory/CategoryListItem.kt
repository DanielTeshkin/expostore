package com.expostore.api.pojo.getcategory

import com.google.gson.annotations.SerializedName

data class CategoryListItem(
    @SerializedName("count")  val count: Int,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("products") val products: List<Product>
)
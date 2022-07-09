package com.expostore.api.pojo.getcategory
import com.google.gson.annotations.SerializedName


data class Category(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("products") val products: ArrayList<CategoryProduct>?,
    @SerializedName("count") val count: Int?
)
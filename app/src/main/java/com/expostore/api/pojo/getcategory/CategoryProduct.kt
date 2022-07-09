package com.expostore.api.pojo.getcategory


import com.google.gson.annotations.SerializedName


data class CategoryProduct(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("images") var images: ArrayList<ImageResponseData>?,
    @SerializedName("price") val price: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("promotion") val promotion: String?,
    @SerializedName("is_liked") val like: Boolean?
)

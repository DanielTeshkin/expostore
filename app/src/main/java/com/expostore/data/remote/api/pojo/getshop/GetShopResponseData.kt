package com.expostore.data.remote.api.pojo.getshop

import com.expostore.data.remote.api.pojo.getreviews.Review
import com.expostore.data.remote.api.response.ProductResponse
import com.google.gson.annotations.SerializedName

data class GetShopResponseData(
        @SerializedName("id") val id: String="",
        @SerializedName("products") val products: List<ProductResponse> = listOf(),
        @SerializedName("rating") val rating: String?="",
        @SerializedName("reviews") val reviews: List<Review>? = listOf(),
        @SerializedName("name")  val name: String="",
        @SerializedName("shopping_center") val shoppingCenter: String="",
        @SerializedName("address")  val address: String="",
        @SerializedName("lat") val lat: String="",
        @SerializedName("long") val long: String="",
        @SerializedName("image") val image: String="",
        @SerializedName("phone") val phone: String="",

        )

package com.expostore.api.pojo.getshop

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.pojo.getreviews.Review
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class GetShopResponseData(
        @JsonProperty("id") val id: String,
        @JsonProperty("products") val products: ArrayList<CategoryProduct>?,
        @JsonProperty("rating") val rating: String?,
        @JsonProperty("reviews") val reviews: ArrayList<Review>?,
        @SerializedName("name") @JsonProperty("name") val name: String,
        @SerializedName("shopping_center") @JsonProperty("shopping_center") val shoppingCenter: String,
        @SerializedName("address") @JsonProperty("address") val address: String,
        @JsonProperty("lat") val lat: String,
        @JsonProperty("long") val long: String,
        @JsonProperty("image") val image: String?,
        @JsonProperty("owner") val owner: String
        )

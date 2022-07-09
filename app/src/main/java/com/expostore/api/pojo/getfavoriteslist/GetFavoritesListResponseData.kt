package com.expostore.api.pojo.getfavoriteslist

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.response.ProductResponse
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class GetFavoritesListResponseData(
    @SerializedName("id") val id: String?,
    @SerializedName("product") val product: ProductResponse,
    @SerializedName("notes") val notes: String?,
    @SerializedName("user") val user: String?
)

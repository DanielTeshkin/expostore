package com.expostore.api.pojo.selectfavorite

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class SelectFavoriteResponseData(
    @SerializedName("id") val id: String?,
    @SerializedName("notes") val notes: String?,
    @SerializedName("user") val user: String?,
    @SerializedName("product") val product: String?
)
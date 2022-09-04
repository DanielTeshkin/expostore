package com.expostore.data.remote.api.pojo.getfavoriteslist

import com.expostore.data.remote.api.response.ProductResponse
import com.google.gson.annotations.SerializedName

data class GetFavoritesListResponseData(
    @SerializedName("id") val id: String?,
    @SerializedName("product") val product: ProductResponse,
    @SerializedName("notes") val notes: String?,
    @SerializedName("user") val user: String?
)

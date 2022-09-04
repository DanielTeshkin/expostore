package com.expostore.data.remote.api.pojo.selectfavorite

import com.google.gson.annotations.SerializedName

data class SelectFavoriteResponseData(
    @SerializedName("id") val id: String?="",
    @SerializedName("notes") val notes: String?="",
    @SerializedName("user") val user: String?="",
    @SerializedName("product") val product: String?=""
)
data class SelectFavoriteTenderResponseData(
    @SerializedName("id") val id: String?,
    @SerializedName("notes") val notes: String?,
    @SerializedName("user") val user: String?,
    @SerializedName("tender") val tender: String?
)
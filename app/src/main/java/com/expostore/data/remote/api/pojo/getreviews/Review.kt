package com.expostore.data.remote.api.pojo.getreviews

import com.expostore.data.remote.api.response.AuthorResponse
import com.expostore.data.remote.api.response.ImageResponse
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.data.remote.api.response.ShopResponse
import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: ArrayList<ImageResponse>?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("promotion") val promotion:String?,
    @SerializedName("product") val productResponse: ProductResponse?,
    @SerializedName("text") val text: String?,
    @SerializedName("shop") val shop: ShopResponse?,
    @SerializedName("date_created") val dateCreated: String?,
    @SerializedName("author") val author: AuthorResponse,
)

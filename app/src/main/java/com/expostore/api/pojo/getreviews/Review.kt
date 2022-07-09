package com.expostore.api.pojo.getreviews

import com.expostore.api.pojo.getchats.ResponseImage
import com.expostore.api.response.AuthorResponse
import com.expostore.api.response.ImageResponse
import com.expostore.api.response.ProductResponse
import com.expostore.api.response.ShopResponse
import com.expostore.model.product.ShopModel
import com.fasterxml.jackson.annotation.JsonProperty
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

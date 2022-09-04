package com.expostore.data.remote.api.pojo.addreview

import com.google.gson.annotations.SerializedName

data class AddReviewResponseData(
        @SerializedName("id") val id: String="",
        @SerializedName("rating") val rating: Int=0,
        @SerializedName("text") val text: String?="",
        @SerializedName("date_created") val dateCreated: String="",
        @SerializedName("author") val author: String="",
        @SerializedName("product") val product: String="",
        @SerializedName("images") val images: List<String>?= listOf()
)

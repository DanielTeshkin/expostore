package com.expostore.data.remote.api.pojo.addreview

import com.google.gson.annotations.SerializedName

data class AddReviewRequestData(
    @SerializedName("rating") val rating: Int=0,
    @SerializedName("text") val text: String?="",
    @SerializedName("images") val images: List<String> = listOf()
)

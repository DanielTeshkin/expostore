package com.expostore.api.pojo.addreview

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class AddReviewRequestData(
    @SerializedName("rating") val rating: Int,
    @SerializedName("text") val text: String?,
    @SerializedName("images") val images: ArrayList<String>
)

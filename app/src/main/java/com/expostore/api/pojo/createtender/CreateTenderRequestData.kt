package com.expostore.api.pojo.createtender

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class CreateTenderRequestData(
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("price_from") val priceFrom: String,
        @SerializedName("price_up_to") val priceUpTo: String,
        @SerializedName("location") val location: String,
        @SerializedName("images") val images: MutableList<String>,
        @SerializedName("category") val category:String?,
        @SerializedName("count") val count: Int?,
        @SerializedName("communication_type")
        val communicationType: String? = null
)

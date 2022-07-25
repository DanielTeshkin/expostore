package com.expostore.api.pojo.createtender

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class CreateTenderRequestData(
        @SerializedName("title") val title: String?=null,
        @SerializedName("description") val description: String?=null,
        @SerializedName("price_from") val priceFrom: Double?=null,
        @SerializedName("price_up") val priceUpTo: Double?=null,
        @SerializedName("location") val location: String?=null,
        @SerializedName("images") val images: MutableList<String>,
        @SerializedName("category") val category:String?,
        @SerializedName("count") val count: Int?,
        @SerializedName("communication_type")
        val communicationType: String? = null,
        @SerializedName("shop")
        val shop:String?=null,
        @SerializedName("status")
        val status:String?=null
)

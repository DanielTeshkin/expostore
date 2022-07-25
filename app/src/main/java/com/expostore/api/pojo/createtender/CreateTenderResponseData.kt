package com.expostore.api.pojo.createtender

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class CreateTenderResponseData(
        @SerializedName("id") val id: String?="",
        @SerializedName("title") val title: String?="",
        @SerializedName("description") val description: Double?=null,
        @SerializedName("price_from") val priceFrom: Double?=null,
        @SerializedName("price_up") val priceUpTo: Double?=null,
        @SerializedName("location") val location: String?="",
        @SerializedName("images") val images: ArrayList<String> ?= listOf<String>() as ArrayList<String>,
        @SerializedName("category") val category: String?=null,
        @SerializedName("author") val author: String?="",
        @SerializedName("shop") val shop: String?="",
        @SerializedName("count") val count: Int?=0,
        @SerializedName("lat") val lat:Double?=null,
        @SerializedName("long") val long:Double?=null,
        @SerializedName("status") val status:String?=null,




        )

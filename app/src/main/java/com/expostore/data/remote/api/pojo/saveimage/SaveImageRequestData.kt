package com.expostore.data.remote.api.pojo.saveimage

import com.google.gson.annotations.SerializedName

data class SaveImageRequestData(
        @SerializedName("image") val image: String = "",
        @SerializedName("extensions") val extensions: String="",

)
data class SaveFileRequestData(
        @SerializedName("file") val file: String = "",
        @SerializedName("extensions") val extensions: String="",
        @SerializedName("filename") val filename:String?=null
)
data class SaveFileResponseData(
        @SerializedName("files_id") val files:List<String> = listOf()
)
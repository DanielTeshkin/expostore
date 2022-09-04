package com.expostore.data.remote.api.pojo.getchats

import com.google.gson.annotations.SerializedName

data class MessageRequest(
    @SerializedName("id") val id: String?= null,
    @SerializedName("images") val images: ArrayList<String> = arrayListOf(),
    @SerializedName("text") val text: String? =null,
    @SerializedName("date_created") val dateCreated: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("chat_files") val chatFiles: List<String> = listOf()

 )

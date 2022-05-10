package com.expostore.api.pojo.getchats

import com.google.gson.annotations.SerializedName

data class MessageRequest(
    @SerializedName("id") val id: String?= null,
    @SerializedName("images") val images: ArrayList<String> = arrayListOf(),
    @SerializedName("text") val text: String? ="",
    @SerializedName("date_created") val dateCreated: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("chat_files") val chatFiles: ArrayList<String> = arrayListOf()

 )

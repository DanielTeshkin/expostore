package com.expostore.api.pojo.getchats

import com.google.gson.annotations.SerializedName

data class FileOrImageMessage(
    @SerializedName("images") val images: ArrayList<String> = arrayListOf(),
    @SerializedName("chat_files") val chatFiles: ArrayList<String> = arrayListOf(),
    @SerializedName("date_created") val dateCreated: String? = null,
@SerializedName("status") val status: String? = null,
@SerializedName("author") val author: String? = null,
    @SerializedName("id") val id: String?= null
 )
package com.expostore.data.remote.api.pojo.getchats


import com.expostore.data.remote.api.response.ImageResponse
import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("id")  val  id : String="",
    @SerializedName("images")  val images: ArrayList<ImageResponse>? = null,
    @SerializedName("text")  val text: String,
    @SerializedName("date_created")  val  dateCreated : String="",
    @SerializedName("status") val  status: String="",
    @SerializedName("author") val  author: String,
    @SerializedName("chat_files") val  chatFiles : ArrayList<ChatFile> = arrayListOf()
    ) {

}

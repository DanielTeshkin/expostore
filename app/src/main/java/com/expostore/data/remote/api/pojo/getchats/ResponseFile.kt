package com.expostore.data.remote.api.pojo.getchats

import com.google.gson.annotations.SerializedName

data class ResponseFile(
    @SerializedName("id")val id:String="",
    @SerializedName("chat_file") val chat_file:String="",
    @SerializedName("filename") val filename:String="",
    @SerializedName("date_created") val date_created : String="",
    @SerializedName("author") val author : String="",


    )

package com.expostore.api.pojo.getchats

import com.google.gson.annotations.SerializedName

data class ChatFile(
    @SerializedName("id") val id:String,
    @SerializedName( "chat_file") val file :String,
    @SerializedName( "filename") val name:String

)

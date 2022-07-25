package com.expostore.api.request

import com.google.gson.annotations.SerializedName

data class NoteRequest(
    @SerializedName("notes") val text:String?=null,

 )


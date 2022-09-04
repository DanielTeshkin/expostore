package com.expostore.data.remote.api.request

import com.google.gson.annotations.SerializedName

data class NoteRequest(
    @SerializedName("notes") val text:String?=null,

 )


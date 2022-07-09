package com.expostore.api.request

import com.google.gson.annotations.SerializedName

data class NoteRequest(
    @SerializedName("text") val text:String,
    @SerializedName("product") val product:String?=null,
    @SerializedName("user") val user:String?=null
 )
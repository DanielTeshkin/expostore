package com.expostore.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class NoteResponse(
    @SerializedName("id") val id:String ="",
    @SerializedName("text") val text:String="",
    @SerializedName("product") val product:String="",
    @SerializedName ("date_created") val date_created:String="",
    @SerializedName ("user") val user:String=""
)

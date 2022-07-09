package com.expostore.api.pojo.getchats

import com.google.gson.annotations.SerializedName

data class RequestFile(
    @SerializedName("date_created") val date_created:String="",
    @SerializedName("filename") val filename: String,
)

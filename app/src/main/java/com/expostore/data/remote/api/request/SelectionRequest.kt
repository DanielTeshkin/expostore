package com.expostore.data.remote.api.request

import com.google.gson.annotations.SerializedName

data class SelectionRequest(
    @SerializedName("name") val name:String,
    @SerializedName("products") val products:List<String>
)

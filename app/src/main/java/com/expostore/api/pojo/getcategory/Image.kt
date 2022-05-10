package com.expostore.api.pojo.getcategory

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("file") val `file`: String,
    @SerializedName("id")val id: String
)
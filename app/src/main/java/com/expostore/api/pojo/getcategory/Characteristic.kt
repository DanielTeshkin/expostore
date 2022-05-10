package com.expostore.api.pojo.getcategory

import com.google.gson.annotations.SerializedName

data class Characteristic(
    @SerializedName("characteristic") val characteristic: String,
    @SerializedName("id")val id: String,
    @SerializedName("value")val value: String
)
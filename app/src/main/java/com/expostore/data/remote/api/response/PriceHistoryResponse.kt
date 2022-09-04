package com.expostore.data.remote.api.response

import com.google.gson.annotations.SerializedName

data class PriceHistoryResponse(
    @SerializedName("price") val price:String,
    @SerializedName("date_create") val date_create:String,
)

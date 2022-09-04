package com.expostore.data.remote.api.pojo.getchats

import com.expostore.data.remote.api.response.ImageResponse
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id"     ) var id     : String= "",
    @SerializedName("name"   ) var name   : String= "",
    @SerializedName("images" ) var images : ArrayList<ImageResponse> = arrayListOf()
)
data class TenderChatResponse(
    @SerializedName("id"     ) var id     : String= "",
    @SerializedName("title"   ) var name   : String= "",
    @SerializedName("images" ) var images : ArrayList<ImageResponse> = arrayListOf()
)
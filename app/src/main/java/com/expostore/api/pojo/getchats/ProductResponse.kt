package com.expostore.api.pojo.getchats

import com.expostore.api.response.ImageResponse
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id"     ) var id     : String= "",
    @SerializedName("name"   ) var name   : String= "",
    @SerializedName("images" ) var images : ArrayList<ImageResponse> = arrayListOf()
)
package com.expostore.api.pojo.getchats

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id"     ) var id     : String?           = null,
    @SerializedName("name"   ) var name   : String?           = null,
    @SerializedName("images" ) var images : ArrayList<ResponseImage> = arrayListOf()
)
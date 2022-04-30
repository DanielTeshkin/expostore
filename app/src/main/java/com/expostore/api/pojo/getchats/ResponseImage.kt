package com.expostore.api.pojo.getchats

import com.google.gson.annotations.SerializedName

data class ResponseImage(
    @SerializedName("id"   ) var id   : String,
    @SerializedName("file" ) val file : String

)

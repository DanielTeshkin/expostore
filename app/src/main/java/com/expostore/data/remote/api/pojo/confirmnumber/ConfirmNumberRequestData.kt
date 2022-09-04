package com.expostore.data.remote.api.pojo.confirmnumber

import com.google.gson.annotations.SerializedName

data class ConfirmNumberRequestData(
    @SerializedName("phone") val phone: String?=""
)

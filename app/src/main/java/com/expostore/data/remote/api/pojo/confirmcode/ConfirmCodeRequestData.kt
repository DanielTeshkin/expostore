package com.expostore.data.remote.api.pojo.confirmcode

import com.google.gson.annotations.SerializedName

data class ConfirmCodeRequestData(
    @SerializedName("phone") val phone: String?,
    @SerializedName("code") val code: String?
)

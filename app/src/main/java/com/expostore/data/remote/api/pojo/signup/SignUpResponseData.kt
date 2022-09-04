package com.expostore.data.remote.api.pojo.signup

import com.google.gson.annotations.SerializedName

data class SignUpResponseData(
    @SerializedName("refresh") val refresh: String?="",
    @SerializedName("access") val access: String?=""
)

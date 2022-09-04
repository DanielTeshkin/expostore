package com.expostore.data.remote.api.base

import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @field:SerializedName("detail") val detail: String = "",
    @field:SerializedName("code") val code: String = ""
)
package com.expostore.api.base

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

/**
 * @author Fedotov Yakov
 */
data class ErrorResponse(
    @field:SerializedName("detail") val detail: String = "",
    @field:SerializedName("code") val code: String = ""
)
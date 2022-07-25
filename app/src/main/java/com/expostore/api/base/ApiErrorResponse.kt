package com.expostore.api.base

import com.fasterxml.jackson.annotation.JsonProperty


data class ApiErrorResponse<T>(
    @JsonProperty("detail")
    val message: String = "",
) : BaseApiResponse<T>() {
    val exception: ApiException
        get() = ApiException(message)
}
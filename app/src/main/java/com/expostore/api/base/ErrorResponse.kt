package com.expostore.api.base

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Fedotov Yakov
 */
data class ErrorResponse(
    @JsonProperty("detail") val detail: String = ""
)
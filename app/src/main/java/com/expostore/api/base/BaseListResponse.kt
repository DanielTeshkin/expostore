package com.expostore.api.base

import com.expostore.api.pojo.getlistproduct.Product
import com.fasterxml.jackson.annotation.JsonProperty


data class BaseListResponse <T>(
    @JsonProperty("count") val count: Int = 0,
    @JsonProperty("next") val next: String? = null,
    @JsonProperty("previous") val previous: String? = null,
    @JsonProperty("results") val results: List<T> = listOf()
)

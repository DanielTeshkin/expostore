package com.expostore.api.pojo.getlistproduct

import com.expostore.api.pojo.getcategory.CategoryProduct
import com.fasterxml.jackson.annotation.JsonProperty

data class GetListProductResponseData(
    @JsonProperty("count") val count: Int?,
    @JsonProperty("next") val next: String?,
    @JsonProperty("previous") val previous: String?,
    @JsonProperty("results") val results: ArrayList<Product>?
)

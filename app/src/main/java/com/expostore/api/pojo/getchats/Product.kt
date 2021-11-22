package com.expostore.api.pojo.getchats

import com.fasterxml.jackson.annotation.JsonProperty

data class Product(
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val name: String?
)
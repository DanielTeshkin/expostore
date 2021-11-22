package com.expostore.api.pojo.getchats

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatItem(
    @JsonProperty("id") val id: String?,
    @JsonProperty("product") val product: Product?,
    @JsonProperty("date_created") val date: String,
    @JsonProperty("messages") val messages: ArrayList<String>?
)
package com.expostore.data.remote.api.pojo.gettenderlist

import com.fasterxml.jackson.annotation.JsonProperty

data class TenderImage(
        @JsonProperty("id") val id: String,
        @JsonProperty("file") val file: String?
)
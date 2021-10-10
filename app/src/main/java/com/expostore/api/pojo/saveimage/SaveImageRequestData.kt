package com.expostore.api.pojo.saveimage

import com.fasterxml.jackson.annotation.JsonProperty

data class SaveImageRequestData(
        @JsonProperty("image") val image: String,
        @JsonProperty("extensions") val extensions: String
)

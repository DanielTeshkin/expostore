package com.expostore.api.pojo.saveimage

import com.fasterxml.jackson.annotation.JsonProperty

data class SaveImageResponseData(
        @JsonProperty("id") val id: String
)

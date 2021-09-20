package com.expostore.api.pojo.confirmnumber

import com.fasterxml.jackson.annotation.JsonProperty

data class ConfirmNumberResponseData(
    @JsonProperty("phone") val phone: String?
)

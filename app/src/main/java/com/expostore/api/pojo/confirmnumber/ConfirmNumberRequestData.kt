package com.expostore.api.pojo.confirmnumber

import com.fasterxml.jackson.annotation.JsonProperty

data class ConfirmNumberRequestData(
    @JsonProperty("phone") val phone: String?
)

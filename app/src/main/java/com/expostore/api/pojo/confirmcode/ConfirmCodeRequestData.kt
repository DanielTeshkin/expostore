package com.expostore.api.pojo.confirmcode

import com.fasterxml.jackson.annotation.JsonProperty

data class ConfirmCodeRequestData(
    @JsonProperty("phone") val phone: String?,
    @JsonProperty("code") val code: String?
)

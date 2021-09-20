package com.expostore.api.pojo.signup

import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpResponseData(
    @JsonProperty("refresh") val refresh: String?,
    @JsonProperty("access") val access: String?
)

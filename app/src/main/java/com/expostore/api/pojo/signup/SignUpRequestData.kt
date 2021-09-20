package com.expostore.api.pojo.signup

import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpRequestData(
    @JsonProperty("username") val username: String?,
    @JsonProperty("password") val password: String?
)

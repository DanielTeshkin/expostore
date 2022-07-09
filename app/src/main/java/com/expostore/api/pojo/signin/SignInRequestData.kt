package com.expostore.api.pojo.signin

import com.fasterxml.jackson.annotation.JsonProperty

data class SignInRequestData(
    @JsonProperty("username") val username: String?,
    @JsonProperty("password") val password: String?
)

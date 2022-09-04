package com.expostore.data.remote.api.pojo.signin

import com.fasterxml.jackson.annotation.JsonProperty

data class SignInResponseData(
    @JsonProperty("refresh") val refresh: String? = null,
    @JsonProperty("access") val access: String? = null
)

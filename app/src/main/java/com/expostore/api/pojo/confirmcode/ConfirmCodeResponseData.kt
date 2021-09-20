package com.expostore.api.pojo.confirmcode

import com.fasterxml.jackson.annotation.JsonProperty

data class ConfirmCodeResponseData(
    @JsonProperty("message") val message: String?
    )

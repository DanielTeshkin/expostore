package com.expostore.api.pojo.addshop

import com.fasterxml.jackson.annotation.JsonProperty

data class AddShopRequestData(
    @JsonProperty("phone") val phone: String?
    )

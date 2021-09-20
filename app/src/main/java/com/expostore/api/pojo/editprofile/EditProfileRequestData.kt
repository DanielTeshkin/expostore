package com.expostore.api.pojo.editprofile

import com.fasterxml.jackson.annotation.JsonProperty

data class EditProfileRequestData(
    @JsonProperty("last_name") val last_name: String?,
    @JsonProperty("first_name") val first_name: String?,
    @JsonProperty("patronymic") val patronymic: String?,
    @JsonProperty("email") val email: String?,
    @JsonProperty("city") val city: Int?
)

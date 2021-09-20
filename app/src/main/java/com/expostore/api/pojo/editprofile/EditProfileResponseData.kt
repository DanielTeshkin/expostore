package com.expostore.api.pojo.editprofile

import com.fasterxml.jackson.annotation.JsonProperty

data class EditProfileResponseData(
    @JsonProperty("id") val id: String?,
    @JsonProperty("last_name") val lastName: String?,
    @JsonProperty("first_name") val firstName: String?,
    @JsonProperty("patronymic") val patronymic: String?,
    @JsonProperty("email") val email: String?,
    @JsonProperty("city") val city: String?,
    @JsonProperty("push_token") val pushToken: String?
    )

package com.expostore.api.pojo.editprofile

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class EditProfileRequestData(
    @SerializedName("last_name") val last_name: String?="",
    @SerializedName("first_name") val first_name: String?="",
    @SerializedName("patronymic") val patronymic: String?="",
    @SerializedName("email") val email: String?="",
    @SerializedName("city") val city: String? =""
)

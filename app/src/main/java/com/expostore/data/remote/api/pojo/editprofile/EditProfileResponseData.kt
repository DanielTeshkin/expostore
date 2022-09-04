package com.expostore.data.remote.api.pojo.editprofile


import com.google.gson.annotations.SerializedName

data class EditProfileResponseData(
    @SerializedName("id") val id: String?="",
    @SerializedName("last_name") val lastName: String?="",
    @SerializedName("first_name") val firstName: String?="",
    @SerializedName("patronymic") val patronymic: String?="",
    @SerializedName("email") val email: String?="",
    @SerializedName("city") val city: String?="",
    @SerializedName("push_token") val pushToken: String?=""
    )

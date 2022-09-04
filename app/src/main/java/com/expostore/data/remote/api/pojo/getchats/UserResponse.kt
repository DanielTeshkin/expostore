package com.expostore.data.remote.api.pojo.getchats

import com.expostore.data.remote.api.response.ImageResponse
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id"         ) var id        : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("online"     ) var online    : String? = null,
    @SerializedName("username"   ) var username  : String? = null,
    @SerializedName("avatar"     ) var avatar    : ImageResponse? = ImageResponse()
)
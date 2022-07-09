package com.expostore.api.pojo.getchats

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.expostore.api.response.ImageResponse
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class UserResponse(
    @SerializedName("id"         ) var id        : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("online"     ) var online    : String? = null,
    @SerializedName("username"   ) var username  : String? = null,
    @SerializedName("avatar"     ) var avatar    : ImageResponse? = ImageResponse()
)
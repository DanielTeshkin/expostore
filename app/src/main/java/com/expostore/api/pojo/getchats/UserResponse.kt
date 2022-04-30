package com.expostore.api.pojo.getchats

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class UserResponse(
    @SerializedName("id"         ) var id        : String,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("online"     ) var online    : String,
    @SerializedName("username"   ) var username  : String,
    @SerializedName("avatar"   ) var avatar  : String? = null
)
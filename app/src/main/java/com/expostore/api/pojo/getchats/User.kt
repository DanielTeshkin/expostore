package com.expostore.api.pojo.getchats

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class User(
    @JsonProperty("id") val id: String?,
    @JsonProperty("last_name") val lastName: String?,
    @JsonProperty("first_name") val firstName: String?,
    @JsonProperty("online") val online: String
)
package com.expostore.api.pojo.getchats

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Chat(
    @JsonProperty("id") val id: String?,
    @JsonProperty("items_chat") val itemsChat: String?,
    @JsonProperty("seller") val seller: User,
    @JsonProperty("buyer") val buyer: User
)
package com.expostore.api.pojo.getcategory

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class ImageResponseData(
    @JsonProperty("id") val id: String?,
    @JsonProperty("file") val file: String?
): Serializable


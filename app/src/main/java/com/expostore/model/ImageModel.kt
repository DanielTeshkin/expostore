package com.expostore.model

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.ImageResponseData
import com.expostore.api.response.ImageResponse
import kotlinx.android.parcel.Parcelize

/**
 * @author Fedotov Yakov
 */
@Parcelize
data class ImageModel(
    val id: String = "",
    val file: String = ""
): Parcelable

val ImageResponse.toModel: ImageModel
    get() = ImageModel(
        id ?: "",
        file ?: ""
    )
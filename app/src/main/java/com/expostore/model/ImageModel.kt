package com.expostore.model

import android.os.Parcelable
import com.expostore.data.remote.api.response.ImageResponse
import com.expostore.data.local.db.enities.chat.ImageDao
import com.expostore.data.local.db.enities.chat.MessageImageDao
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

val ImageDao.toModel:ImageModel
get() = ImageModel(id,file)

val MessageImageDao.toModel:ImageModel
get() = ImageModel(id,file)
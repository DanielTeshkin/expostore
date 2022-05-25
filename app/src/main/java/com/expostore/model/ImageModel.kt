package com.expostore.model

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.ImageResponseData
import com.expostore.api.response.ImageResponse
import com.expostore.db.enities.chat.ImageDao
import com.expostore.db.enities.chat.MessageImageDao
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
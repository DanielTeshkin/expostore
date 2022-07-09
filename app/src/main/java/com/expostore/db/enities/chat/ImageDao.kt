package com.expostore.db.enities.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.api.pojo.getchats.ResponseImage
import com.expostore.api.response.ImageResponse


@Entity
data class ImageDao(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "file")val file: String )

val ImageResponse.toDao : ImageDao
get() = ImageDao(id,file)
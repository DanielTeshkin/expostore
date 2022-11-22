package com.expostore.data.local.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.google.gson.annotations.SerializedName

@Entity(tableName = "local_images")
data class SaveImageRequestDao(
    @PrimaryKey
    @ColumnInfo(name = "image") val image: String = "",
    @ColumnInfo(name="extensions") val extensions: String="",
)
val SaveImageRequestDao.toRequest:SaveImageRequestData
get() = SaveImageRequestData(image, extensions)
val SaveImageRequestData.toDao :SaveImageRequestDao
get() = SaveImageRequestDao(image, extensions)
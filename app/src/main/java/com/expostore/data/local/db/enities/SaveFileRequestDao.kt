package com.expostore.data.local.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData

@Entity(tableName = "local_files")
data class SaveFileRequestDao(
    @PrimaryKey
    @ColumnInfo(name="file") val file: String = "",
    @ColumnInfo(name="extensions") val extensions: String="",
    @ColumnInfo(name = "filename") val filename:String?=""
)
val SaveFileRequestDao.toRequest:SaveFileRequestData
get() = SaveFileRequestData(file, extensions, filename)
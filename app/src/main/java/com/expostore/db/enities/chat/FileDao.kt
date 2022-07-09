package com.expostore.db.enities.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.api.pojo.getchats.ChatFile

@Entity
data class FileDao(
    @PrimaryKey
    @ColumnInfo(name = "id") val id:String,
    @ColumnInfo(name = "file")  val file :String,
    @ColumnInfo(name = "name") val name:String
)

val ChatFile.toDao : FileDao
get() = FileDao(id, file, name)

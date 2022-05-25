package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.ChatFile
import com.expostore.db.enities.chat.FileDao
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileChat( val id:String,
                     val file :String,
                    val name:String):Parcelable
val ChatFile.toModel :FileChat
get() =  FileChat(
    id, file, name
)
val FileDao.toModel:FileChat
get() = FileChat(id,file, name)
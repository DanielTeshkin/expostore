package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.getchats.ChatFile
import com.expostore.data.local.db.enities.chat.FileDao
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileChat( val id:String="",
                     val file :String="",
                    val name:String=""):Parcelable
val ChatFile.toModel :FileChat
get() =  FileChat(
    id, file, name
)
val FileDao.toModel:FileChat
get() = FileChat(id,file, name)
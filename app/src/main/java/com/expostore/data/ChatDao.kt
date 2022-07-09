package com.expostore.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.api.pojo.getchats.MessageResponse
import com.expostore.api.pojo.getchats.ProductResponse
import com.expostore.model.chats.DataMapping.MainChat


@Entity(tableName = "chat_dao")
data class ChatDao(
 @PrimaryKey
 @ColumnInfo(name = "id") val id : String ="",
 @ColumnInfo(name = "messages") val messageResponses : String = "",
 @ColumnInfo(name = "product") val product : String = "",
 @ColumnInfo(name = "date_created") val dateCreated : String? = null
)

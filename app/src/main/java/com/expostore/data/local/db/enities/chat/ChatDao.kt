package com.expostore.data.local.db.enities.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.model.chats.DataMapping.MainChat

@Entity(tableName = "chats")
data class ChatDao(
    @PrimaryKey
    @ColumnInfo(name="id") val id : String,
                   @ColumnInfo(name="items_chat" ) val itemsChat : List<ItemChatDao> = listOf(),
                   @ColumnInfo(name="seller") val seller : UserDao,
                   @ColumnInfo(name="buyer" ) val buyer : UserDao,
                   @ColumnInfo(name="request_user") val request_user: UserDao
)




val MainChat.toDao: ChatDao
get() = ChatDao(id,itemsChat.map { it.toDao },seller.toDao,buyer.toDao,request_user.toDao)

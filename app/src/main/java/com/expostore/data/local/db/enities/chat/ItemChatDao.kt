package com.expostore.data.local.db.enities.chat

import androidx.room.*
import com.expostore.data.remote.api.pojo.getchats.TenderChatResponse
import com.expostore.model.chats.DataMapping.*
import com.expostore.model.tender.TenderModel


@Entity(tableName = "chat_dao")

data class ItemChatDao(
 @PrimaryKey
 @ColumnInfo(name = "id") val id : String ="",
 @ColumnInfo(name = "messages") val messages : MutableList<Message>? = mutableListOf(),
 @ColumnInfo(name = "product")
 val product : Product?=null,
 @ColumnInfo(name = "date_created") val dateCreated : String = "",
 @ColumnInfo(name = "tender")
 val tender:TenderChat?=null
)


val ItemChat.toDao:ItemChatDao
get() =
 ItemChatDao(id,messages, product = product,dateCreated?:"",tender)

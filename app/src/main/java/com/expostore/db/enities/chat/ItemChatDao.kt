package com.expostore.db.enities.chat

import androidx.room.*
import com.expostore.api.pojo.getchats.ChatResponse
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.MessageResponse
import com.expostore.api.pojo.getchats.ProductResponse
import com.expostore.db.enities.product.ProductDao
import com.expostore.db.enities.product.toDao
import com.expostore.model.chats.DataMapping.*
import com.expostore.model.product.ProductModel
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
 val tender:TenderModel?=null
)


val ItemChat.toDao:ItemChatDao
get() =
 ItemChatDao(id,messages, product = product,dateCreated?:"",tender)

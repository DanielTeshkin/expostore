package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.ProductResponse
import com.expostore.db.enities.chat.ItemChatDao
import com.expostore.model.tender.TenderModel
import com.expostore.model.tender.toModel

import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemChat(val id : String,
                    val messages : MutableList<Message>? = mutableListOf(),
                    val product : Product?=null,
                    val dateCreated : String?,
                    val tender:TenderModel?=null
): Parcelable


val ItemChatResponse.toModel : ItemChat
    get()= ItemChat(
    id=id,
    messageResponses?.map { it.toModel }?.cast() ?: mutableListOf(),
   product= product?.toModel,
        tender=tender?.toModel,
    dateCreated=dateCreated
    )

val ItemChatDao.toModel : ItemChat
get() = ItemChat(id, messages as MutableList<Message>,product,dateCreated)



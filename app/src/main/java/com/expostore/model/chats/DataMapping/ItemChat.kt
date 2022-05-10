package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.ProductResponse

import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemChat(val id : String,
                    val messages : ArrayList<Message>,
                    val product : Product,
                    val dateCreated : String? ): Parcelable


val ItemChatResponse.toModel : ItemChat
    get()= ItemChat(
    id=id,
    messageResponses.map { it.toModel }.cast(),
   product= product!!.toModel ,
    dateCreated=dateCreated
    )



package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.getchats.TenderChatResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class TenderChat (
    val name:String,
    val id:String,
    val images:List<ImageChat>
        ):Parcelable
val TenderChatResponse.toModel:TenderChat
get() = TenderChat(name,id,images.map { it.toModel })
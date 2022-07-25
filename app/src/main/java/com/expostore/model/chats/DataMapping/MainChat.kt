package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.ChatResponse
import com.expostore.db.enities.chat.ChatDao
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainChat(val id : String,
                    val itemsChat : List<ItemChat> = listOf(),
                    val seller : User,
                    val buyer : User,
                    val request_user: User
) : Parcelable
  val ChatResponse.toModel:MainChat
      get()=MainChat(
          id=id?:"",
          itemsChatResponse.orEmpty().map{it.toModel},
          seller.toModel ?:User(),
          buyer.toModel ?:User(),
          request_user.toModel ?:User()
      )

val ChatDao.toModel:MainChat
get() = MainChat(id,itemsChat.map { it.toModel },seller.toModel,buyer.toModel,request_user.toModel)






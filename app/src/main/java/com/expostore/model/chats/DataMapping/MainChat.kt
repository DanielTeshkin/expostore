package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.getchats.ChatResponse
import com.expostore.data.local.db.enities.chat.ChatDao
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
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
          itemsChatResponse?.map{it.toModel}?: mutableListOf(),
          seller.toModel ?:User(),
          buyer.toModel ?:User(),
          request_user.toModel ?:User()
      )
val MainChat.toInfoModel : InfoItemChat
  get() = InfoItemChat(identify()[1],
    identify()[0],
    chatsId(),
    imagesProduct(),
    productsName(),
    identify()[3])

val ChatDao.toModel:MainChat
get() = MainChat(id,itemsChat.map { it.toModel },seller.toModel,buyer.toModel,request_user.toModel)






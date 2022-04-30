package com.expostore.api.pojo.getchats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.ResponseMainChat
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainChat(val id : String,
                    val itemsChat : List<ItemChat>,
                    val seller : User,
                    val buyer : User,
                    val request_user: User
) : Parcelable
  val ResponseMainChat.toModel:MainChat
      get()=MainChat(
          id=id?:"",
          itemsChatResponse.orEmpty().map{it.toModel},
          seller.toModel,
          buyer.toModel,
          request_user.toModel
      )




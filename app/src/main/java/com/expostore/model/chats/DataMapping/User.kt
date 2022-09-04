package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.getchats.UserResponse
import com.expostore.data.local.db.enities.chat.UserDao
import com.expostore.model.ImageModel
import com.expostore.model.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class User( val id: String="" ,
          var lastName  : String? = "",
          var firstName : String? = "",
          var online    : String="",
          var username  : String="",
          var avatar  : ImageModel? = ImageModel()
) :Parcelable
 val UserResponse.toModel:User
  get() = User(
      id=id?:"",
      lastName=lastName,
      firstName = firstName,
      online=online?:"",
      username =username?:"",
      avatar = avatar?.toModel
  )

val UserDao.toModel : User
get() = User(id, lastName, firstName, online, username)
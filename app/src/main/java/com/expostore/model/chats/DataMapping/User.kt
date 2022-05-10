package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.UserResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class User( val id: String ,
          var lastName  : String? = null,
          var firstName : String? = null,
          var online    : String,
          var username  : String,
          var avatar  : String? = null) :Parcelable
 val UserResponse.toModel:User
  get() = User(
      id=id,
      lastName=lastName,
      firstName = firstName,
      online=online,
      username =username,
      avatar = avatar
  )
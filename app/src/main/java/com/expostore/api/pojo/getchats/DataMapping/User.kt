package com.expostore.api.pojo.getchats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.UserResponse
import com.google.gson.annotations.SerializedName
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
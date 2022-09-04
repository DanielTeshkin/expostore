package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.data.remote.api.response.ImageResponse


import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageChat( val id  : String,
                         val file : String):Parcelable

val ImageResponse.toModel:ImageChat
  get() = ImageChat(
      id=id,
      file =file
  )

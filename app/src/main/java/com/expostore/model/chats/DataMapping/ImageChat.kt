package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.ResponseImage
import com.expostore.api.response.ImageResponse
import com.expostore.databinding.ImageReceviedItemBinding


import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageChat( val id  : String,
                         val file : String):Parcelable

val ImageResponse.toModel:ImageChat
  get() = ImageChat(
      id=id,
      file =file
  )

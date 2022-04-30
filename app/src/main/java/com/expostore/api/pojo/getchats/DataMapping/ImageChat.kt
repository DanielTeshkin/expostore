package com.expostore.api.pojo.getchats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.ResponseImage



import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageChat( val id  : String,
                         val file : String):Parcelable

val ResponseImage.toModel:ImageChat
  get() = ImageChat(
      id=id,
      file =file
  )

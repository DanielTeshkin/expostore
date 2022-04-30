package com.expostore.api.pojo.getchats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.MessageResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    val id: String,
    val images: List<ProductImage>? = null,
    val text: String,
    val dateCreated: String,
    val status: String,
    val author: String,
    val chatFiles: List<String>? = arrayListOf()):Parcelable

val MessageResponse.toModel:Message
     get()= Message(
         id=id,
         imageResponsImages.orEmpty().map { it.toModel },
         text=text,
         dateCreated=dateCreated,
         status=status,
         author=author,
         chatFiles.map { it }
     )

fun <T> List<T>.cast():ArrayList<T>{
  return  this as ArrayList<T>
}
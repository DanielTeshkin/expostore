package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.api.pojo.getchats.MessageResponse
import com.expostore.model.ImageModel
import com.expostore.model.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    val id: String="",
    val images: List<ImageModel>? = null,
    val text: String,
    val dateCreated: String="",
    val status: String?="",
    val author: String,
    val chatFiles: List<FileChat>? = arrayListOf()):Parcelable

val MessageResponse.toModel:Message
     get()= Message(
         id=id,
         images.orEmpty().map { it.toModel },
         text=text,
         dateCreated=dateCreated,
         status=status,
         author=author,
         chatFiles?.map { it.toModel }
     )

fun <T> List<T>.cast():ArrayList<T>{
  return  this as ArrayList<T>
}

fun createMessage(text:String,author:String,images:List<ImageModel>): Message {
    return Message(text = text, author = author, images = images)
}
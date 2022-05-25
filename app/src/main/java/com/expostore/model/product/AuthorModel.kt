package com.expostore.model.product

import android.os.Parcelable
import com.expostore.api.response.AuthorResponse
import com.expostore.model.ImageModel
import com.expostore.model.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorModel(
    val city: String? = "",
    val lastName: String = "",
    val id: String = "",
    val firstName: String = "",
    val email: String = "",
    val username: String = "",
    val avatar:ImageModel = ImageModel()

): Parcelable

fun AuthorModel.name():String{
   return if(lastName!=null&&firstName!=null) "$firstName $lastName"
    else{ username }
}

val AuthorResponse.toModel: AuthorModel
    get() = AuthorModel(
        city ?: "",
        lastName ?: "",
        id ?: "",
        firstName ?: "",
        email ?: "",
        username ?: "",
        avatar?.toModel ?:  ImageModel()
    )

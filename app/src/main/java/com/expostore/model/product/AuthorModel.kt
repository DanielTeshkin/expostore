package com.expostore.model.product

import android.os.Parcelable
import com.expostore.data.remote.api.response.AuthorResponse
import com.expostore.model.ImageModel
import com.expostore.model.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorModel(
    val city: String? = "",
    val lastName: String? = null,
    val id: String = "",
    val firstName: String? = null,
    val email: String = "",
    val username: String = "",
    val avatar:ImageModel = ImageModel()

): Parcelable

fun AuthorModel.name():String{
   return if(firstName!=null) "$firstName $lastName"
    else username
}

val AuthorResponse.toModel: AuthorModel
    get() = AuthorModel(
        city ?: "",
        lastName ,
        id ?: "",
        firstName ,
        email ?: "",
        username ?: "",
        avatar?.toModel ?:  ImageModel()
    )

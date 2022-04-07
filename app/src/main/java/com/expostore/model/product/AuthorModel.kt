package com.expostore.model.product

import android.os.Parcelable
import com.expostore.api.response.AuthorResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorModel(
    val city: String = "",
    val lastName: String = "",
    val id: String = "",
    val firstName: String = "",
    val email: String = "",
    val username: String = ""
): Parcelable

val AuthorResponse.toModel: AuthorModel
    get() = AuthorModel(
        city ?: "",
        lastName ?: "",
        id ?: "",
        firstName ?: "",
        email ?: "",
        username ?: ""
    )

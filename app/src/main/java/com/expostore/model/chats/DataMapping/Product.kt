package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(var id: String?           = null,
                   var name: String?           = null,
                   var images: List<ImageChat> = listOf()):Parcelable

val com.expostore.api.pojo.getchats.ProductResponse.toModel:Product
         get() = Product(
             id =id,
             name =name,
           images.orEmpty().map { it.toModel }
         )


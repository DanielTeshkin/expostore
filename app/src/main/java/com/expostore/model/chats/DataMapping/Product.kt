package com.expostore.model.chats.DataMapping

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.getchats.ProductResponse
import com.expostore.data.local.db.enities.product.ProductDao
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(var id: String         = "",
                   var name: String          = "",
                   var images: List<ImageChat>? = null):Parcelable

val ProductResponse.toModel:Product
         get() = Product(
             id =id,
             name =name,
           images.map { it.toModel }
         )

val ProductDao.toModel : Product
get() = Product(id, name)


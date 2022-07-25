package com.expostore.model.product

import android.os.Parcelable
import com.expostore.api.response.ShopResponse
import com.expostore.model.ImageModel
import com.expostore.model.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopModel(
    val image: ImageModel = ImageModel(),
    val address: String = "",
    val author: AuthorModel = AuthorModel(),
    val name: String = "",
    val id: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val shoppingCenter: String = "",
    val phone:String? =""
): Parcelable

val ShopResponse.toModel: ShopModel
    get() = ShopModel(
        image?.toModel ?: ImageModel(),
        address ?: "",
        author?.toModel ?: AuthorModel(),
        name ?: "",
        id ?: "",
        lat ?: 0.0,
        lng ?: 0.0,
        shoppingCenter ?: "",
        phone
    )

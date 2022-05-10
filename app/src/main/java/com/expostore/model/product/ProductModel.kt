package com.expostore.model.product

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.Characteristic
import com.expostore.api.response.ProductResponse
import com.expostore.model.ImageModel
import com.expostore.model.toModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    val owner: String = "",
    val shortDescription: String = "",
    val descriptionBlocked: String = "",
    val images: List<ImageModel> = emptyList(),
    val characteristics: List<Character> = emptyList(),
    val shop: ShopModel = ShopModel(),
    val author: AuthorModel = AuthorModel(),
    val dateCreated: String = "",
    val rating: Double = 0.0,
    val count: Int = 0,
    val longDescription: String = "",
    val isVerified: Boolean = false,
    val priceHistory: List<String> = emptyList(),
    val endDateOfPublication: String = "",
    val price: String = "",
    val name: String = "",
    val id: String = "",
    val category: String = "",
    val isLiked: Boolean = false,
    val promotion: String = "",
    val status: String = "",
    val communicationType: String = ""
):Parcelable

val ProductResponse.toModel: ProductModel
    get() = ProductModel(
        owner ?: "",
        shortDescription ?: "",
        descriptionBlocked ?: "",
        images.orEmpty().map { it.toModel },
        characteristics.orEmpty().map { it.toModel },
        shop?.toModel ?: ShopModel(),
        author?.toModel ?: AuthorModel(),
        dateCreated ?: "",
        rating ?: 0.0,
        count ?: 0,
        longDescription ?: "",
        isVerified ?: false,
        priceHistory.orEmpty(),
        endDateOfPublication ?: "",
        price ?: "",
        name ?: "",
        id ?: "",
        category ?: "",
        isLiked ?: false,
        promotion ?: "",
        status ?: "",
        communicationType ?: ""
    )
@Parcelize
data class Character( val characteristic: String,
                     val id: String,
                    val value: String):Parcelable
val Characteristic.toModel:Character
get()= Character(characteristic, id, value)
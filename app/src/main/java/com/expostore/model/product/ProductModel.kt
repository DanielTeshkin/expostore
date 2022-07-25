package com.expostore.model.product

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.pojo.getcategory.Characteristic
import com.expostore.api.pojo.getcategory.CharacteristicResponse
import com.expostore.api.pojo.getproduct.ProductPromotion
import com.expostore.api.response.ProductResponse
import com.expostore.model.ImageModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.category.toModel
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
    val category: ProductCategoryModel?=null,
    val isLiked: Boolean = false,
    val promotion: PromotionModel = PromotionModel(),
    val status: String = "",
    val communicationType: String =""
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
        category?.toModel ?: null,
        isLiked ?: false,
        promotion?.toModel ?: PromotionModel(),
        status ?: "",
        communicationType ?: ""
    )


@Parcelize
data class Character(val characteristic: CharacteristicModeL?= CharacteristicModeL(),
                     val id: String?="",
                     val char_value:String?="",
                     val bool_value:Boolean?=false
                    ):Parcelable
val Characteristic.toModel:Character
get()= Character(id = id?:"",  characteristic = characteristic?.toModel, char_value = char_value, bool_value = bool_value)


@Parcelize
data class CharacteristicModeL(
                      val id: String="",
                      val value: List<String>? = listOf(),
                       val name:String? ="",
                      val type: String?=""):Parcelable


val CharacteristicResponse.toModel :CharacteristicModeL
get() = CharacteristicModeL(id,value,name,type)



@Parcelize
data class PromotionModel(
    val percentageDiscount: Int?=0,
     val valueDiscount: String?="",
     val end_time: String?=""
):Parcelable
val ProductPromotion.toModel: PromotionModel
get() = PromotionModel(percentageDiscount, valueDiscount, endTime)
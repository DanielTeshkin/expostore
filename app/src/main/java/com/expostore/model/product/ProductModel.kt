package com.expostore.model.product

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.*
import com.expostore.api.pojo.getproduct.ProductPromotion
import com.expostore.api.response.ProductResponse
import com.expostore.model.ImageModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.category.toModel
import com.expostore.model.review.ReviewModel
import com.expostore.model.review.toModel
import com.expostore.model.toModel
import com.expostore.ui.fragment.product.qrcode.ProductQrCodeViewModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

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
    val communicationType: String ="",
    val reviews:List<ReviewModel> = listOf(),
    val articul:String="",
    val qrcode:ProductQrCodeModel=ProductQrCodeModel()
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
       price?:"",
        name ?: "",
        id ?: "",
        category?.toModel ?: null,
        isLiked ?: false,
        promotion?.toModel ?: PromotionModel(),
        status ?: "",
        communicationType ?: "",
        reviews = reviews.map { it.toModel },
        articul = articul?:"",
        qrcode = ProductQrCodeModel(qrcode.product,qrcode.qr_code_image)
    )


@Parcelize
data class Character(val characteristic: CharacteristicModeL?= CharacteristicModeL(),
                     val id: String?="",
                     val char_value:String?="",
                     val bool_value:Boolean?=false,
                     val selected_item:SelectedItemModel?= SelectedItemModel(),
                     val selected_items:List<SelectedItemModel> ? = listOf()

                    ):Parcelable
val Characteristic.toModel:Character
get()= Character(id = id?:"",  characteristic = characteristic?.toModel, char_value = char_value, bool_value = bool_value,
    selected_item = selected_item?.toModel, selected_items = selected_items?.map { it.toModel }
)

@Parcelize
data class SelectedItemModel(
    val id: String="",
    val value: String ="",
):Parcelable
val SelectedItem.toModel :SelectedItemModel
get() = SelectedItemModel(id,value)


@Parcelize
data class CharacteristicModeL(
                      val id: String="",
                      val name:String? ="",
                      val type: String?=""):Parcelable


val CharacteristicResponse.toModel :CharacteristicModeL
get() = CharacteristicModeL(id,name,type)



@Parcelize
data class PromotionModel(
    val percentageDiscount: Int?=0,
     val valueDiscount: String?="",
     val end_time: String?=""
):Parcelable
val ProductPromotion.toModel: PromotionModel
get() = PromotionModel(percentageDiscount, valueDiscount, endTime)

@Parcelize
data class ProductQrCodeModel(
    val product:String="",
    val qr_code_image:String=""

):Parcelable

fun String.priceSeparator():String {
    var iteration = length -1
    var counter=0
    val stroke= mutableListOf<String>()

    while (iteration != 0) {
        if(this[iteration] == '.'){
            stroke.add(this[iteration].toString())
            iteration -= 1
            break
        }
        stroke.add(this[iteration].toString())
        println(this[iteration].toString())
        iteration -= 1
    }


    do {
        if(counter%3==0&&counter!=0){
            stroke.add(" ")
            stroke.add(this[iteration].toString())
        }
        else{
            stroke.add(this[iteration].toString())
        }
        counter+=1

        iteration-=1
    }while(iteration>=0)


    return stroke.joinToString(separator ="").reversed()
}


package com.expostore.model.tender

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.Image
import com.expostore.api.pojo.getcategory.ImageResponseData
import com.expostore.api.pojo.getproduct.Shop
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.api.pojo.gettenderlist.TenderCategory
import com.expostore.api.response.ImageResponse
import com.expostore.model.ImageModel
import com.expostore.model.product.AuthorModel
import com.expostore.model.product.Character
import com.expostore.model.product.CharacteristicModeL
import com.expostore.model.product.ShopModel
import com.expostore.model.product.toModel
import com.expostore.model.toModel
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TenderModel(val id: String="",
                        val isLiked : Boolean? = null,
                       val title: String?="",
                       val description: String?=null,
                       val price: String?=null,
                       val location: String?=null,
                       val author: AuthorModel= AuthorModel(),
                       val  date_created :String="",
                       val images: List<ImageModel>? = emptyList(),
                       val category: TenderCategoryModel? = null,
                       val count:Int=0,
                       val shopModel: ShopModel=ShopModel(),
                       val lat:Double?=null,
                       val long:Double?=null,
                       var communicationType : String?= null,
                       var status : String? = null,
                       val characteristicModel: List<Character>?=null,


):Parcelable

val Tender.toModel : TenderModel
get() = TenderModel(id=id?:"", isLiked = isLiked, title = title,description,price,location,author?.toModel?:AuthorModel(),
    date_created=dateCreated?:"",images.map { it.toModel },category=category?.toModel,count=count?:0,shop?.toModel?: ShopModel(), lat = lat,
    long = long, communicationType = communicationType, status = status, characteristicModel = characteristics?.map { it.toModel }
)

fun TenderModel.priceRange():String =price?:"неизвестно"


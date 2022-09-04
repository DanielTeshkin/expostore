package com.expostore.model.tender

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.gettenderlist.Tender
import com.expostore.model.ImageModel
import com.expostore.model.product.*
import com.expostore.model.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TenderModel(val id: String="",
                        val isLiked : Boolean = false,
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
                       val elected: ElectedModel=ElectedModel()


):Parcelable

val Tender.toModel : TenderModel
get() = TenderModel(id=id?:"", isLiked = isLiked?:false, title = title,description,price,location,author?.toModel?:AuthorModel(),
    date_created=dateCreated?:"",images.map { it.toModel },category=category?.toModel,count=count?:0,shop?.toModel?: ShopModel(), lat = lat,
    long = long, communicationType = communicationType, status = status, characteristicModel = characteristics?.map { it.toModel },
    elected = ElectedModel(elected?.id?:"",elected?.notes?:"")
)

fun TenderModel.priceRange():String =price?:"неизвестно"


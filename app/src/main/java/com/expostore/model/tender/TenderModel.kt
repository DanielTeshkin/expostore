package com.expostore.model.tender

import android.os.Parcelable
import com.expostore.api.pojo.getcategory.Image
import com.expostore.api.pojo.getcategory.ImageResponseData
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.api.pojo.gettenderlist.TenderCategory
import com.expostore.api.response.ImageResponse
import com.expostore.model.ImageModel
import com.expostore.model.product.AuthorModel
import com.expostore.model.product.toModel
import com.expostore.model.toModel
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TenderModel(val id: String,
                       val title: String?,
                       val description: String?,
                       val priceFrom: String?,
                       val priceUpTo: String?,
                       val location: String?,
                       val author: AuthorModel,
                       val  date_created :String,
                       val images: List<ImageModel>,
                       val category: List<TenderCategoryModel>):Parcelable

val Tender.toModel : TenderModel
get() = TenderModel(id,title,description,priceFrom,priceUpTo,location,author.toModel,date_created,images.map { it.toModel },category.map { it.toModel })

fun TenderModel.priceRange():String {
    return if(priceFrom!=null &&priceUpTo!=null) {
        "oт $priceFrom до $priceUpTo рублей"
    }
           else if(priceFrom!=null&&priceUpTo==null){
        "oт $priceFrom рублей"
    }
     else if(priceFrom==null&&priceUpTo!=null){
        "до $priceUpTo рублей"
    }
    else{"незвестно"}
}
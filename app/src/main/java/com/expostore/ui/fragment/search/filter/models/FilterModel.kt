package com.expostore.ui.fragment.search.filter.models

import android.os.Parcelable
import com.expostore.api.request.FilterRequest
import com.expostore.api.request.toRequestModel
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    val name:String?=null,
    val price_min:Int?=null,
    val price_max:Int?=null,
    val city:String?=null,
    val lat:Double?=null,
    val long:Double?=null,
    val sort:String?=null,
    val category: String?=null,
    val promotion:Boolean?=null,
    val characteristics:List<CharacteristicFilterModel>?=null):Parcelable
val FilterModel.toRequest:FilterRequest
get() = FilterRequest(category = category, price_max = price_max, price_min = price_min,
    city = city,q=name, characteristics = characteristics?.map { it.toRequestModel }, sort = sort, lat = lat, long = long, promotion = promotion)
val FilterRequest.toModel : FilterModel
get() = FilterModel(name = q, price_min = price_min, price_max = price_max, promotion = promotion, category = category,
       sort = sort,lat=lat, city = city, long = long, characteristics = characteristics?.map { it.toModel }
    )
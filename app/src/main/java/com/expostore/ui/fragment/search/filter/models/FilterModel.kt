package com.expostore.ui.fragment.search.filter.models

import android.os.Parcelable
import com.expostore.model.category.CharacteristicFilterModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    val name:String?=null,
    val price_min:Int?=null,
    val price_max:Int?=null,
    val city:String?=null,
    val category: String?=null,
    val promotion:Boolean?=null,
    val characteristics:List<CharacteristicFilterModel>?=null):Parcelable

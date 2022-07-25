package com.expostore.model.category

import android.os.Parcelable
import com.expostore.api.request.CharacteristicFilterRequest

import kotlinx.android.parcel.Parcelize

@Parcelize
 data class CharacteristicFilterModel(
val characteristic:String?=null,
val char_value:String?=null,
val bool_value:Boolean?=null,
val selected_item:String?=null,
val selected_items:List<String>?=null,
val left_input:String?=null,
val right_input:String?=null

 ):Parcelable
val CharacteristicFilterRequest.toModel:CharacteristicFilterModel
get() = CharacteristicFilterModel(characteristic, char_value, bool_value, selected_item, selected_items, left_input,right_input)
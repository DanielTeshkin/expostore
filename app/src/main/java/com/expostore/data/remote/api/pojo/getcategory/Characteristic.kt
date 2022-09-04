package com.expostore.data.remote.api.pojo.getcategory

import com.google.gson.annotations.SerializedName

data class Characteristic(
    @SerializedName("characteristic") val characteristic: CharacteristicResponse?,
    @SerializedName("id")val id: String?,
    @SerializedName("char_value")val char_value:String?,
    @SerializedName("bool_value")  val bool_value:Boolean?,
    @SerializedName("selected_item")  val selected_item:SelectedItem?=null,
    @SerializedName("selected_items")  val selected_items:List<SelectedItem>?=null,

)
data class SelectedItem(
    @SerializedName("id") val id: String="",
    @SerializedName("value")  val value: String ="",
)

data class CharacteristicResponse(
    @SerializedName("id") val id: String="",
    @SerializedName("name") val name:String?="",
    @SerializedName("type") val type:String?="",
    )
data class CharacteristicRequest(
    @SerializedName("characteristic") val characteristic: String?,
    @SerializedName("char_value")val char_value:String?=null,
    @SerializedName("bool_value")  val bool_value:Boolean?=null,
    @SerializedName("selected_item")  val selected_item:String?=null,
    @SerializedName("selected_items")  val selected_items:List<String>?=null,
)
data class CharacteristicProductSelection(
    @SerializedName("id") val id: String,
    @SerializedName("value")  val value: List<String>,
    @SerializedName("name") val name:String,
    @SerializedName("type") val type:String,
    @SerializedName("char_value")val char_value:String,
    @SerializedName("bool_value")  val bool_value:Boolean
)
package com.expostore.api.pojo.getcategory

import com.google.gson.annotations.SerializedName

data class Characteristic(
    @SerializedName("characteristic") val characteristic: CharacteristicResponse?,
    @SerializedName("id")val id: String?,
    @SerializedName("char_value")val char_value:String?,
    @SerializedName("bool_value")  val bool_value:Boolean?

)

data class CharacteristicResponse(
    @SerializedName("id") val id: String="",
    @SerializedName("value")  val value: List<String>? = listOf(),
    @SerializedName("name") val name:String?="",
    @SerializedName("type") val type:String?="",
    )

data class CharacteristicProductSelection(
    @SerializedName("id") val id: String,
    @SerializedName("value")  val value: List<String>,
    @SerializedName("name") val name:String,
    @SerializedName("type") val type:String,
    @SerializedName("char_value")val char_value:String,
    @SerializedName("bool_value")  val bool_value:Boolean
)
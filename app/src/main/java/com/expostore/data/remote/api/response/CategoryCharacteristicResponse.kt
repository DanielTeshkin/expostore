package com.expostore.data.remote.api.response

import com.expostore.data.remote.api.pojo.getcategory.CharacteristicResponse
import com.expostore.data.remote.api.pojo.getcategory.SelectedItem
import com.google.gson.annotations.SerializedName



data class CharacteristicComparison(
	@SerializedName("characteristic") val characteristic: CategoryCharacteristicResponse?,
	@SerializedName("id")val id: String?,
	@SerializedName("char_value")val char_value:String?,
	@SerializedName("bool_value")  val bool_value:Boolean?,
	@SerializedName("selected_item")  val selected_item:Value?=null,
	@SerializedName("selected_items")  val selected_items:List<Value>?=null,

	)

data class CategoryCharacteristicResponse(

	@field:SerializedName("list_value")
	val listValue: List<Value>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null

)

data class Value(
	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("id")
	val id: String? = null,
)
